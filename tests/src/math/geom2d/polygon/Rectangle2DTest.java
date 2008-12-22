/*
 * File : Rectangle2DTest.java
 *
 * Project : geometry
 *
 * ===========================================
 * 
 * This library is free software; you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 2.1 of the License, or (at
 * your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY, without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library. if not, write to :
 * The Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307, USA.
 * 
 * author : Legland
 * Created on 31 d�c. 2003
 */
package math.geom2d.polygon;

import math.geom2d.Shape2D;
import math.geom2d.domain.Domain2D;
import math.geom2d.transform.AffineTransform2D;
import junit.framework.TestCase;

/**
 * @author Legland
 */
public class Rectangle2DTest extends TestCase {

	/**
	 * Constructor for Rectangle2DTest.
	 * @param arg0
	 */
	public Rectangle2DTest(String arg0) {
		super(arg0);
	}

	public static void main(String[] args) {
		junit.awtui.TestRunner.run(Rectangle2DTest.class);
	}

	public void testGetVerticesNumber() {
		Rectangle2D rect = new Rectangle2D(10, 20, 30, 40);
		assertEquals(rect.getVertexNumber(), 4);
	}

	public void testGetDistance(){
		Rectangle2D rect = new Rectangle2D(10, 20, 30, 40);
		assertEquals(rect.getDistance(10, 20), 0, Shape2D.ACCURACY);		
		assertEquals(rect.getDistance(40, 20), 0, Shape2D.ACCURACY);		
		assertEquals(rect.getDistance(40, 60), 0, Shape2D.ACCURACY);		
		assertEquals(rect.getDistance(10, 60), 0, Shape2D.ACCURACY);		
	}

	/*
	 * Test for boolean equals(Rectangle2D)
	 */
	public void testEqualsRectangle2D() {
		Rectangle2D rect1 = new Rectangle2D(10, 20, 30, 40);
		Rectangle2D rect2 = new Rectangle2D(40, 20, 40, 30, Math.PI/2);
		assertTrue(rect1.equals(rect2));
	}

	public void testTransform(){
		// just to test if Rectangle2D is still a FillShape2D after transform
		Rectangle2D rect = new Rectangle2D(10, 20, 40, 50);
		AffineTransform2D aff = new AffineTransform2D(2, 1, 0, 3, 2, 0);
		AffineTransform2D aff2 = new AffineTransform2D(1, 3, .5, 2, .3, 1);
		assertTrue((rect.transform(aff) instanceof math.geom2d.domain.Domain2D));
		assertTrue((rect.transform(aff).transform(aff2) instanceof Domain2D));
	}
}
