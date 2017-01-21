/** 
 * @author finalObject
 *         http://www.finalobject.cn
 *         i@finalobject.cn
 *         https://github.com/finalObject
 * @date 2017��1��21�� ����12:33:48
 * @version 1.0
 */
package simulationOfFanInAreaMCM;

import java.util.ArrayList;

public class Car {
	public double maxAccSpeed;
	public double length, width;// ���ĳ���
	public double x, y;
	// Ŀ�공����Ϣ
	double[][] lanes;

	public double maxSpeed = 19.4;
	// ����Ӧ���趨Ϊǰ��120�������
	public double maxDir = 60.0 / 180 * Math.PI;
	public double maxAccDir = 30;
	public double speed = 0;

	// �ýǶȱ�ʾ���г�����Ϊ0��˳ʱ�뷽������
	public double direction = 0;
	// Ŀ��λ��
	public double tarX, tarY;
	// ��Ӧʱ��
	public double time = 0.5;
	// ��Ե�޹ʻ������ĸ���
	public double chanceOfChange = 0.0001;
	// ��һ�³������������������Ϊ����ת��
	public double safeDis = 10;
	// С����������з����෴ʱ������Ӧ����Ӧ
	public double dangDis = 3;
	// ����λ������Ƕ�����Ҫ����
	public double th1 = 20.0 / 180 * Math.PI;
	// �ж�Σ�յ�����һ���Ƕ���ֵ
	public double th2 = 30.0 / 180 * Math.PI;

	// ���²�������Ҫ��ǰ�趨
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
		// ѡ�񳵵�
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

	// ��黷�����ı������ٶȺͷ���
	public void check(Area area, ArrayList<Car> cars) {
		if(Math.abs(reactTime)>0.001)return;
		double[] result = { 0, 0 };
		// �������������ľ���
		int size = cars.size();
		// �������г���
		for (int i = 0; i < size; i++) {
			Car car = cars.get(i);
			if (this == car)
				continue;
			result = getDistanceWithObj(car.x, car.y);
			if (result[0] < dangDis && (Math.abs(direction - result[1]) < th2)) {
				// ��ʼ��Ӧ
				reactTime = time;
				// ���������ǰ����С��ĳ���Ƕȣ� ��ô��Ҫ����
				if (Math.abs(result[1]) < th1) {
					hopeSpeed = 0;
				}
				// ȷ����Ҫ��ת�ķ���
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
		// ��������ľ���
		result = getDistanceWithArea(area);
		if (result[0] < dangDis && (Math.abs(direction - result[1]) < th2)) {
			// ˢ�·�Ӧʱ��
			reactTime = time;
			// �ٶȲ���Ҫ�仯
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
		// ��Ե�޹ʻ�����
		// �趨�ٶȺͷ���
		if (Math.random() < chanceOfChange) {
			changeLanes();
		}

		// û�г��������������ʱ��
		// ����Ƿ���ʻ��Ŀ��·�ڵķ�����
		result = getDistanceWithObj(tarX, tarY);
		if (Math.abs(direction - result[1]) > th2) {
			reactTime = time;
			hopeDir = result[1];
			if (hopeDir > maxDir)
				hopeDir = maxDir;
			else if (hopeDir < -maxDir)
				hopeDir = -maxDir;
		}
		// ����ٶȣ���Ҫ����
		if (speed < maxSpeed) {
			reactTime = time;
			hopeSpeed = maxSpeed;
		}
	}

	// ��Ӧ��ʱ��ͨ���������ٶȺͷ���ı���ٶ�
	public void act(double stepTime) {
		// ���reactTime�Ӵ���1��Ϊ���ˣ����ж���
		// �Ƚ��������ٶȺͷ����ʵ�֣����иı�
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
		// ���ݼ��ٶȸı��ٶȺͷ���
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
		// �����ٶȺͷ���ı�xy
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
		// ��֪���᲻���������
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
