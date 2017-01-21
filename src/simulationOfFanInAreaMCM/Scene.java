/** 
 * @author finalObject
 *         http://www.finalobject.cn
 *         i@finalobject.cn
 *         https://github.com/finalObject
 * @date 2017年1月21日 上午11:35:05
 * @version 1.0
 */
package simulationOfFanInAreaMCM;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
//管理
public class Scene{
	JFrame frame;
	View view;
	ArrayList<Car> cars;
	Area area;
	double[][] tolls;
	double[][] lanes;
	int numOfToll=8;
	int numOfLanes=3;
	double widthL=3.75;
	double widthT=3.3;
	double lengthSpeed = 93;
	double lengthT = 20;
	
	double stepTime=0.1;
	double flux = 200;//每小时车流量
	
	double[][] carsData={
			{5.5,2,4},
			{3,2.2,4.5},
			{2.5,2.8,8}
	};
	//视图有关的参数
	Boolean start = false;
	JSlider sliderOfScale;
	JSlider sliderOfShiftX;
	JSlider sliderOfShiftY;
	
	
	
	public Scene(){
		super();
	}
	public int step(){
		int size=cars.size();
		//check
		//act
		//run
		for(int i=0;i<size;i++){
			Car car=cars.get(i);
			car.check(area, cars);
			car.act(stepTime);
			car.run(stepTime);
			System.out.println(car.speed);
		}

		//检查事故
		//生成出口处的车
		frame.repaint();
		return 0;
	} 
	public void init(){
		cars=new ArrayList<>();
		area = new Area(widthT*numOfToll, widthL*numOfLanes, lengthT, lengthSpeed);
		//计算收费亭坐标和道路坐标
		tolls=new double[numOfToll][2];
		lanes=new double[numOfLanes][2];
		for (int i=0;i<numOfToll;i++){
			tolls[i][0]=0;
			tolls[i][1]=(i+1)*widthT-widthT/2;
		}
		for (int i=0;i<numOfLanes;i++){
			lanes[i][0]=lengthSpeed+lengthT;
			lanes[i][1]=(i+1)*widthL-widthL/2;
		}
		
		//int appearCar = getPossionVariable(flux/3600*stepTime);
		//cars.addAll(getRandownCars(1));
		cars.add(new Car(2.3, 2, 3, 0, tolls[0][1], lanes));
		//cars.add(new Car(5.7, 2, 4, x, y, lanes))
		frame = new JFrame();
		view = new View(area,cars,this);
		frame.add(view);
		
		
		JButton btn1 = new JButton("START");
		btn1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (start) {
					start = false;
					btn1.setText("START");
				} else {
					start = true;
					btn1.setText("STOP");
				}

			}

		});
		JButton btn2 = new JButton("STEP");
		btn2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				step();

			}

		});
		view.setLayout(null);
		btn1.setBounds(0, 400, 100, 40);
		btn2.setBounds(100, 400, 100, 40);
		view.add(btn1);
		view.add(btn2);
		
		frame.setTitle("fan in area");
		frame.setSize(1000, 580);
		frame.setVisible(true);
	}
	public ArrayList<Car> getRandownCars(int number){
		if (number >=numOfToll)number=numOfToll;
		ArrayList<Car> cars=new ArrayList<>();
		Boolean flag = false;
		for(int i=0;i<number;i++){
			int type=(int)(Math.random()*(carsData.length));
			int tollNumber=0;
			while(!flag){
				flag=true;
				tollNumber = (int)(Math.random()*(numOfToll));
				for (int j=0;j<cars.size();j++){
					if(tolls[tollNumber][1]==cars.get(j).y)
					{
						flag=false;
					}
				}
			}
			flag=false;
			cars.add(new Car(carsData[type][0], carsData[type][1], carsData[type][2],
					tolls[tollNumber][0], tolls[tollNumber][1], lanes));
			
		}
		return cars;
	}
	//泊松随机数
	private static int getPossionVariable(double lamda) {  
        int x = 0;  
        double y = Math.random(), cdf = getPossionProbability(x, lamda);  
        while (cdf < y) {  
            x++;  
            cdf += getPossionProbability(x, lamda);  
        }  
        return x;  
    }    
    private static double getPossionProbability(int k, double lamda) {  
        double c = Math.exp(-lamda), sum = 1;  
        for (int i = 1; i <= k; i++) {  
            sum *= lamda / i;  
        }  
        return sum * c;  
    }  
	public static void main(String[] args) throws InterruptedException{
		Scene scene =new Scene();
		scene.init();
		while (true) {
			if (scene.start) {
				scene.step();
				Thread.sleep(100);
				//System.out.println("step!");
			}else{
				Thread.sleep(100);
				//System.out.println("NoStep!");
			}
		}
		
	}
}
