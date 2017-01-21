/** 
 * @author finalObject
 *         http://www.finalobject.cn
 *         i@finalobject.cn
 *         https://github.com/finalObject
 * @date 2017年1月21日 下午12:33:48
 * @version 1.0
 */
package simulationOfFanInAreaMCM;

import java.util.ArrayList;

public class Car {
	public double maxAccSpeed;
	public double length, width;// 车的长宽
	public double x, y;
	// 目标车道信息
	double[][] lanes;

	public double maxSpeed = 19.4;
	// 方向应该设定为前方120°的扇形
	public double maxDir = 60.0 / 180 * Math.PI;
	public double maxAccDir = 30;
	public double speed = 0;

	// 用角度表示，行车方向为0，顺时针方向增大
	public double direction = 0;
	// 目标位置
	public double tarX, tarY;
	// 反应时间
	public double time = 0.5;
	// 无缘无故换车道的概率
	public double chanceOfChange = 0.0001;
	// 看一下车辆，大于这个距离认为可以转弯
	public double safeDis = 10;
	// 小于这个距离切方向相反时，做出应急反应
	public double dangDis = 3;
	// 车辆位于这个角度内需要减速
	public double th1 = 20.0 / 180 * Math.PI;
	// 判定危险的另外一个角度阈值
	public double th2 = 30.0 / 180 * Math.PI;

	// 以下参数不需要提前设定
	public int tarLanes = 0;
	public double reactTime = 0;
	public double hopeSpeed = 0;
	public double hopeDir = 0;
	public double accSpeed = 0;
	public double accDir = 0;

	public Car(double maxAccSpeed, double width, double length, double x, double y, double[][] lanes) {
		super();
		this.x = x;
		this.y = y;
		this.maxAccSpeed = maxAccSpeed;
		this.length = length;
		this.width = width;
		this.lanes = lanes;
		// 选择车道
		if (lanes == null)
			return;
		int size = lanes.length;
		double minDis = 999999;
		for (int i = 0; i < size; i++) {
			if (getDistanceWithObj(lanes[i][0], lanes[i][1])[0] < minDis) {
				minDis = getDistanceWithObj(lanes[i][0], lanes[i][1])[0];
				tarLanes = i;
			}
		}
		tarX = lanes[tarLanes][0];
		tarY = lanes[tarLanes][1];
	}

	public void display() {
		System.out.println(
				"maxAccSpeed:" + maxAccSpeed + ",width:" + width + ",length:" + length + ",x:" + x + ",y:" + y);
	}

	public void changeLanes() {
		int delta = 0;
		if (tarLanes == 0) {
			delta = 1;
		} else if (tarLanes == lanes.length - 1) {
			delta = -1;
		} else {
			if (Math.random() < 0.5) {
				delta = 1;
			} else {
				delta = -1;
			}
		}
		tarLanes = tarLanes + delta;
		tarX = lanes[tarLanes][0];
		tarY = lanes[tarLanes][1];
	}

	// 检查环境，改变期望速度和方向
	public void check(Area area, ArrayList<Car> cars) {
		if(Math.abs(reactTime)>0.001)return;
		double[] result = { 0, 0 };
		// 检查和其他车辆的距离
		int size = cars.size();
		// 遍历所有车辆
		for (int i = 0; i < size; i++) {
			Car car = cars.get(i);
			if (this == car)
				continue;
			result = getDistanceWithObj(car.x, car.y);
			if (result[0] < dangDis && (Math.abs(direction - result[1]) < th2)) {
				// 开始反应
				reactTime = time;
				// 如果车辆在前方，小于某个角度， 那么需要减速
				if (Math.abs(result[1]) < th1) {
					hopeSpeed = 0;
				}
				// 确定需要旋转的方向
				if (result[1] >= 0) {
					hopeDir = result[1] - Math.PI;
				} else {
					hopeDir = result[1] + Math.PI;
				}
				if (hopeDir > maxDir)
					hopeDir = maxDir;
				else if (hopeDir < -maxDir)
					hopeDir = -maxDir;
				return;
			}
		}
		// 检查和区域的距离
		result = getDistanceWithArea(area);
		if (result[0] < dangDis && (Math.abs(direction - result[1]) < th2)) {
			// 刷新反应时间
			reactTime = time;
			// 速度不需要变化
			hopeSpeed = speed;
			if (result[1] >= 0) {
				hopeDir = result[1] - Math.PI;
			} else {
				hopeDir = result[1] + Math.PI;
			}
			if (hopeDir > maxDir)
				hopeDir = maxDir;
			else if (hopeDir < -maxDir)
				hopeDir = -maxDir;
			return;
		}
		// 无缘无故换车道
		// 设定速度和方向
		if (Math.random() < chanceOfChange) {
			changeLanes();
		}

		// 没有出现上述三种情况时，
		// 检查是否行驶在目标路口的方向上
		result = getDistanceWithObj(tarX, tarY);
		if (Math.abs(direction - result[1]) > th2) {
			reactTime = time;
			hopeDir = result[1];
			if (hopeDir > maxDir)
				hopeDir = maxDir;
			else if (hopeDir < -maxDir)
				hopeDir = -maxDir;
		}
		// 检查速度，需要加速
		if (speed < maxSpeed) {
			reactTime = time;
			hopeSpeed = maxSpeed;
		}
	}

	// 反应延时后通过期望的速度和方向改变加速度
	public void act(double stepTime) {
		// 如果reactTime从大于1归为零了，进行动作
		// 比较期望的速度和方向和实现，进行改变
		if (reactTime > 0) {
			reactTime = reactTime - stepTime;
			if (Math.abs(reactTime) <= 0.001) {
				reactTime = 0;
				if ((hopeSpeed - speed) > 0.00001) {
					accSpeed = maxAccSpeed;
				} else if ((hopeSpeed - speed) < -0.00001) {
					accSpeed = -maxAccSpeed;
				}
				if ((hopeDir - direction) > 0.00001) {
					accDir = maxAccDir;
				} else if ((hopeDir - direction) < 0.00001) {
					accDir = -maxAccDir;
				}
			}

		}
	}

	public void run(double stepTime) {
		// 根据加速度改变速度和方向
		speed += accSpeed * stepTime;
		direction += accDir * stepTime;
		if (accSpeed * (speed - hopeSpeed) > 0) {
			speed = hopeSpeed;
			accSpeed = 0;
		}
		if (accDir * (direction - hopeDir) >= 0) {
			direction = hopeDir;
			accDir = 0;
		}
		// 根据速度和方向改变xy
		x = x + speed * Math.cos(direction) * stepTime;
		y = y + speed * Math.sin(direction) * stepTime;

	}

	public double[] getDistanceWithArea(Area area) {
		double[] result = { 0, 0 };
		double distance1 = Math.abs(y);
		double distance2 = Math.abs(area.widthT - y);
		double distance3 = Math.abs(area.k * x + area.b - y) / (Math.sqrt(area.k * area.k + 1));
		result[0] = distance1;
		result[1] = -Math.PI / 2;
		if (result[0] > distance2) {
			result[0] = distance2;
			result[1] = Math.PI / 2;
		}
		if (result[0] > distance3) {
			result[0] = distance3;
			result[1] = Math.atan(-1 / area.k);
		}
		return result;
	}

	public double[] getDistanceWithObj(double x, double y) {
		// 不知道会不会出现问题
		double[] result = { 0, 0 };
		result[0] = Math.sqrt((x - this.x) * (x - this.x) + (y - this.y) * (y - this.y));
		result[1] = Math.atan((y - this.y) / (x - this.x));
		if ((this.x > x) && (this.y > y)) {
			result[1] = result[1] - Math.PI;
		}
		if ((this.x > x) && (this.y < y)) {
			result[1] = result[1] + Math.PI;
		}
		return result;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Car car1 = new Car(4.6, 4, 2, 6, 2, new double[3][2]);
		Area area = new Area(2 + 4 / Math.sqrt(3), 2, 4, 4);
		car1.check(area, new ArrayList<>());
		System.out.println((car1.getDistanceWithArea(area))[1] / Math.PI * 180);
		System.out.println(car1.hopeDir / Math.PI * 180);
	}

}
