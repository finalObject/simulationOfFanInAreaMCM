/** 
 * @author finalObject
 *         http://www.finalobject.cn
 *         i@finalobject.cn
 *         https://github.com/finalObject
 * @date 2017��1��21�� ����11:35:05
 * @version 1.0
 */
package simulationOfFanInAreaMCM;


import java.util.ArrayList;

import javax.swing.JFrame;
//����
public class Scene{
	JFrame frame;
	View view;
	ArrayList<Car> cars;
	public Scene(){
		
	}
	public void paint(){
		frame = new JFrame();
		view = new View();
		frame.add(view);
		frame.setTitle("fan in area");
		frame.pack();
		frame.setVisible(true);
	}
	public static void main(String[] args){
		Scene scene =new Scene();
		scene.paint();
		
	}
}
