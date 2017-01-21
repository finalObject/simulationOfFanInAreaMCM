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
	public int id;
	public double maxAccSpeed;
	public double length,width;//车的长宽
	public double x,y;
	
	public double maxSpeed=55;
	//方向应该设定为前方120°的扇形
	public double maxDir=60;	
	public double maxAccDir=30;	
	public double speed=0;
	//用角度表示，行车方向为0，顺时针方向增大
	public double direction=0;	
	//反应时间
	public double time=0.5;
	//无缘无故换车道的概率
	public double chanceOfChange=0.1;
	//调小这个可以增大事故率
	public double safeDis=10;
	
	
	//以下参数不需要提前设定
	public double reactTime=0;
	public double hopeSpeed=0;
	public double hopeDir=0;
	public double accSpeed=0;
	public double accDir=0;
	public Car(int id,double maxAccSpeed,double width,double length,double x,double y) {
		super();
		this.x=x;
		this.y=y;
		this.id=id;
		this.maxAccSpeed = maxAccSpeed;
		this.length = length;
		this.width = width;
	}
	//检查环境，改变期望速度和方向
	public void check(Area area,ArrayList<Car> cars){
		//判断速度和方向是否满足要求
		
		//检查和区域的距离
		double[] resultArea=getDistanceWithArea(area);
		if (resultArea[0]<safeDis){
		}
		//检查和其他车辆的距离
		//上述两种情况会把设置的目标位于反向无穷远
		//return
		
		//检查是否行驶在目标路口的方向上
		//设定速度和方向
		
		//无缘无故换车道
		//设定速度和方向
		
	}
	//反应延时后通过期望的速度和方向改变加速度
	public void act(double stepTime){
		//如果reactTime从大于1归为零了，进行动作
		//比较期望的速度和方向和实现，进行改变
		if (reactTime>0){
			reactTime=reactTime-stepTime;
			if (reactTime<=0){
				reactTime=0;
				
			}
			
		}

	}
	public void run(double stepTime){
		//根据加速度改变速度和方向
		speed+=accSpeed*stepTime;
		direction+=accDir*stepTime;
		if(accSpeed*(speed-hopeSpeed)>=0){speed=hopeSpeed;accSpeed=0;}
		if(accDir*(direction-hopeDir)>=0){direction=hopeDir;accDir=0;}
		//根据速度和方向改变xy
		x=x+speed*Math.cos(direction)*stepTime;
		y=y+speed*Math.sin(direction)*stepTime;

	}
	
	public double[] getDistanceWithArea(Area area){
		double[] result = {0,0};
		double distance1=Math.abs(y);
		double distance2=Math.abs(area.widthT-y);
		double distance3=Math.abs(area.k*x+area.b-y)/(Math.sqrt(area.k*area.k+1));
		result[0]=distance1;result[1]=-Math.PI/2;
		if(result[0]>distance2){result[0]=distance2;result[1]=Math.PI/2;}
		if(result[0]>distance3){result[0]=distance3;result[1]=Math.atan(-1/area.k);}
		return result;
	}
	
	public double[] getDistanceWithCar(Car car){
		// 不知道会不会出现问题
		double[] result = {0,0};
		result[0]=Math.sqrt((car.x-this.x)*(car.x-this.x)+(car.y-this.y)*(car.y-this.y));
		result[1]=Math.atan((car.y-this.y)/(car.x-this.x));
		if ((this.x>car.x)&&(this.y>car.y)){result[1]=result[1]-Math.PI;}
		if ((this.x>car.x)&&(this.y<car.y)){result[1]=result[1]+Math.PI;}
		return result;
	}

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Car car1 = new Car(1,4.6,4,2,0,0);
		Car car2 = new Car(2,4.6,4,2,1,1);
		System.out.println((car1.getDistanceWithCar(car2))[1]/Math.PI*180);
	}

}
