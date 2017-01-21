/** 
 * @author finalObject
 *         http://www.finalobject.cn
 *         i@finalobject.cn
 *         https://github.com/finalObject
 * @date 2017年1月21日 上午11:36:22
 * @version 1.0
 */
package simulationOfFanInAreaMCM;



public class Area {
	// 现在假设的形状是斜率为固定值，如果需要修改的话需要修改有点多的东西
	public double widthT, widthL, lengthT, lengthSpeed;
	double k,b;

	public Area(double widthT, double widthL, double lengthT, double lengthSpeed) {
		this.widthT = widthT;
		this.widthL = widthL;
		this.lengthT = lengthT;
		this.lengthSpeed = lengthSpeed;
		this.k=-(widthT-widthL)/lengthSpeed;
		this.b=widthT-this.k*lengthT;
	}

	// 0表示正常，1表示与道路相撞，2表示离开区域
	public int checkArea(double x, double y) {
		// 在区域左侧
		if (x < 0) { return 2; }
		// 在区域右侧
		if (x > lengthSpeed + lengthT) { return 2; }
		// 在区域上侧
		if (y < 0) { return 2; }
		// 在区域下侧
		if ((x<lengthT&&y>widthT)||(x>lengthT&&y>(this.k*x+b))){return 2;}
		return 0;
	}

	public static void main(String[] args){
		Area  area = new Area(2+4/Math.sqrt(3),2,4,4);
		
	}
}
