/** 
 * @author finalObject
 *         http://www.finalobject.cn
 *         i@finalobject.cn
 *         https://github.com/finalObject
 * @date 2017��1��21�� ����11:36:22
 * @version 1.0
 */
package simulationOfFanInAreaMCM;



public class Area {
	// ���ڼ������״��б��Ϊ�̶�ֵ�������Ҫ�޸ĵĻ���Ҫ�޸��е��Ķ���
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

	// 0��ʾ������1��ʾ���·��ײ��2��ʾ�뿪����
	public int checkArea(double x, double y) {
		// ���������
		if (x < 0) { return 2; }
		// �������Ҳ�
		if (x > lengthSpeed + lengthT) { return 2; }
		// �������ϲ�
		if (y < 0) { return 2; }
		// �������²�
		if ((x<lengthT&&y>widthT)||(x>lengthT&&y>(this.k*x+b))){return 2;}
		return 0;
	}

	public static void main(String[] args){
		Area  area = new Area(2+4/Math.sqrt(3),2,4,4);
		
	}
}
