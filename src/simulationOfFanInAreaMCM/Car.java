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
	public int id;
	public double maxAccSpeed;
	public double length,width;//���ĳ���
	public double x,y;
	
	public double maxSpeed=55;
	//����Ӧ���趨Ϊǰ��120�������
	public double maxDir=60;	
	public double maxAccDir=30;	
	public double speed=0;
	//�ýǶȱ�ʾ���г�����Ϊ0��˳ʱ�뷽������
	public double direction=0;	
	//��Ӧʱ��
	public double time=0.5;
	//��Ե�޹ʻ������ĸ���
	public double chanceOfChange=0.1;
	//��С������������¹���
	public double safeDis=10;
	
	
	//���²�������Ҫ��ǰ�趨
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
	//��黷�����ı������ٶȺͷ���
	public void check(Area area,ArrayList<Car> cars){
		//�ж��ٶȺͷ����Ƿ�����Ҫ��
		
		//��������ľ���
		double[] resultArea=getDistanceWithArea(area);
		if (resultArea[0]<safeDis){
		}
		//�������������ľ���
		//�����������������õ�Ŀ��λ�ڷ�������Զ
		//return
		
		//����Ƿ���ʻ��Ŀ��·�ڵķ�����
		//�趨�ٶȺͷ���
		
		//��Ե�޹ʻ�����
		//�趨�ٶȺͷ���
		
	}
	//��Ӧ��ʱ��ͨ���������ٶȺͷ���ı���ٶ�
	public void act(double stepTime){
		//���reactTime�Ӵ���1��Ϊ���ˣ����ж���
		//�Ƚ��������ٶȺͷ����ʵ�֣����иı�
		if (reactTime>0){
			reactTime=reactTime-stepTime;
			if (reactTime<=0){
				reactTime=0;
				
			}
			
		}

	}
	public void run(double stepTime){
		//���ݼ��ٶȸı��ٶȺͷ���
		speed+=accSpeed*stepTime;
		direction+=accDir*stepTime;
		if(accSpeed*(speed-hopeSpeed)>=0){speed=hopeSpeed;accSpeed=0;}
		if(accDir*(direction-hopeDir)>=0){direction=hopeDir;accDir=0;}
		//�����ٶȺͷ���ı�xy
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
		// ��֪���᲻���������
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
