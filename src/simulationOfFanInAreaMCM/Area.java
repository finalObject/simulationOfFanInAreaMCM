/** 
 * @author finalObject
 *         http://www.finalobject.cn
 *         i@finalobject.cn
 *         https://github.com/finalObject
 * @date 2017年1月21日 上午11:36:22
 * @version 1.0
 */
package simulationOfFanInAreaMCM;

import java.awt.Point;

public class Area {
	//现在假设的形状是斜率为固定值，如果需要修改的话需要修改有点多的东西
	double widthT,widthL,lengthT,lengthSpeed;
	public Area(double widthT,double widthL,double lengthT,double lengthSpeed){
		this.widthT=widthT;
		this.widthL=widthL;
		this.lengthT=lengthT;
		this.lengthSpeed=lengthSpeed;
	}
	//0表示正常，1表示与道路相撞，2表示离开区域
	public int checkArea(Point loc){
		return 0;
		
	}
}
