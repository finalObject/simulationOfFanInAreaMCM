/** 
 * @author finalObject
 *         http://www.finalobject.cn
 *         i@finalobject.cn
 *         https://github.com/finalObject
 * @date 2017年1月21日 下午12:22:17
 * @version 1.0
 */
package simulationOfFanInAreaMCM;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class View extends JPanel {
	int DEFAULT_SIZE=400;
	@Override
	public void paint(Graphics g) {
		super.paint(g);
	}
	public Dimension getPreferredSize() {
		return new Dimension(DEFAULT_SIZE , DEFAULT_SIZE);
	}
}
