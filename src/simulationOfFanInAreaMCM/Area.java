/** 
 * @author finalObject
 *         http://www.finalobject.cn
 *         i@finalobject.cn
 *         https://github.com/finalObject
 * @date 2017��1��21�� ����11:36:22
 * @version 1.0
 */
package simulationOfFanInAreaMCM;

import java.awt.Point;

public class Area {
	//���ڼ������״��б��Ϊ�̶�ֵ�������Ҫ�޸ĵĻ���Ҫ�޸��е��Ķ���
	double widthT,widthL,lengthT,lengthSpeed;
	public Area(double widthT,double widthL,double lengthT,double lengthSpeed){
		this.widthT=widthT;
		this.widthL=widthL;
		this.lengthT=lengthT;
		this.lengthSpeed=lengthSpeed;
	}
	//0��ʾ������1��ʾ���·��ײ��2��ʾ�뿪����
	public int checkArea(Point loc){
		return 0;
		
	}
}
