/*
 * File : SimplePolygon2DTest.java
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
 * Created on 3 janv. 2004
 */
package math.geom2d.polygon;

import java.util.ArrayList;

import math.geom2d.AffineTransform2D;
import math.geom2d.Box2D;
import math.geom2d.Point2D;
import math.geom2d.Shape2D;
import math.geom2d.Vector2D;
import math.geom2d.circulinear.CirculinearDomain2D;
import math.geom2d.domain.Boundary2D;
import math.geom2d.line.StraightLine2D;
import junit.framework.TestCase;

/**
 * @author Legland
 */
public class SimplePolygon2DTest extends TestCase {

	/**
	 * Constructor for SimplePolygon2DTest.
	 * @param arg0
	 */
	public SimplePolygon2DTest(String arg0) {
		super(arg0);
	}

	public static void main(String[] args) {
		junit.awtui.TestRunner.run(SimplePolygon2DTest.class);
	}

	public void testGetComplement(){
		// start with a simple rectangle
		Point2D points[] = new Point2D[4];
		points[0] = new Point2D(10, 10);
		points[1] = new Point2D(20, 10);
		points[2] = new Point2D(20, 20);
		points[3] = new Point2D(10, 20);
		SimplePolygon2D poly = new SimplePolygon2D(points);
		
		SimplePolygon2D poly2 = poly.complement();
		assertEquals(poly2.getSignedArea(), -poly.getSignedArea());
	}
	
	public void testTransform_Motion(){
		Point2D points[] = new Point2D[4];
		points[0] = new Point2D(10, 10);
		points[1] = new Point2D(20, 10);
		points[2] = new Point2D(20, 20);
		points[3] = new Point2D(10, 20);
		SimplePolygon2D poly = new SimplePolygon2D(points);
		
		Point2D center = new Point2D(0, 0);
		double angle = Math.PI/2;
		AffineTransform2D transfo = 
			AffineTransform2D.createRotation(center, angle);
		
		Polygon2D poly2 = poly.transform(transfo);
		
		assertFalse(poly2.isEmpty());
		assertTrue(poly2.isBounded());
		
		Point2D centro2 = new Point2D(-15, 15);
		double dist = centro2.getDistance(poly2.getCentroid());
		
		assertTrue(dist < Shape2D.ACCURACY);
	}
	
	public void testTransform_Motion_InvertedPolygon(){
		Point2D points[] = new Point2D[4];
		points[0] = new Point2D(10, 10);
		points[1] = new Point2D(10, 20);
		points[2] = new Point2D(20, 20);
		points[3] = new Point2D(20, 10);
		SimplePolygon2D poly = new SimplePolygon2D(points);
		assertFalse(poly.isBounded());
		
		Point2D center = new Point2D(0, 0);
		double angle = Math.PI/2;
		AffineTransform2D transfo = 
			AffineTransform2D.createRotation(center, angle);
		
		Polygon2D poly2 = poly.transform(transfo);
		
		assertFalse(poly2.isEmpty());
		assertFalse(poly2.isBounded());
		
		Point2D centro2 = new Point2D(-15, 15);
		double dist = centro2.getDistance(poly2.getCentroid());
		
		assertTrue(dist < Shape2D.ACCURACY);
	}
	
	public void testTransform_Reflect(){
		Point2D points[] = new Point2D[4];
		points[0] = new Point2D(10, 10);
		points[1] = new Point2D(20, 10);
		points[2] = new Point2D(20, 20);
		points[3] = new Point2D(10, 20);
		SimplePolygon2D poly = new SimplePolygon2D(points);
		
		Point2D center = new Point2D(0, 0);
		Vector2D vect = new Vector2D(0, 3); 
		StraightLine2D line = new StraightLine2D(center, vect);
		AffineTransform2D transfo = 
			AffineTransform2D.createLineReflection(line);
		
		Polygon2D poly2 = poly.transform(transfo);
		
		assertFalse(poly2.isEmpty());
		assertTrue(poly2.isBounded());
		
		Point2D centro2 = new Point2D(-15, 15);
		double dist = centro2.getDistance(poly2.getCentroid());
		
		assertTrue(dist < Shape2D.ACCURACY);
	}
	
	public void testTransform_Reflect_InvertedPolygon(){
		Point2D points[] = new Point2D[4];
		points[0] = new Point2D(10, 10);
		points[1] = new Point2D(10, 20);
		points[2] = new Point2D(20, 20);
		points[3] = new Point2D(20, 10);
		SimplePolygon2D poly = new SimplePolygon2D(points);
		assertFalse(poly.isBounded());
		
		Point2D center = new Point2D(0, 0);
		Vector2D vect = new Vector2D(0, 3); 
		StraightLine2D line = new StraightLine2D(center, vect);
		AffineTransform2D transfo = 
			AffineTransform2D.createLineReflection(line);
		
		Polygon2D poly2 = poly.transform(transfo);
		
		assertFalse(poly2.isEmpty());
		assertFalse(poly2.isBounded());
		
		Point2D centro2 = new Point2D(-15, 15);
		double dist = centro2.getDistance(poly2.getCentroid());
		
		assertTrue(dist < Shape2D.ACCURACY);
	}
	
	/*
	 * Test for boolean contains(double, double)
	 */
	public void testContainsdoubledouble(){
		
		// start with a simple rectangle
		Point2D points[] = new Point2D[4];
		points[0] = new Point2D(20, 20);
		points[1] = new Point2D(40, 20);
		points[2] = new Point2D(40, 60);
		points[3] = new Point2D(20, 60);
		SimplePolygon2D poly = new SimplePolygon2D(points);
		
		assertTrue(poly.contains(20, 20));
		assertTrue(poly.contains(40, 20));
		assertTrue(poly.contains(40, 60));
		assertTrue(poly.contains(20, 60));
		assertTrue(poly.contains(25, 20));
		assertTrue(poly.contains(25, 40));
		assertTrue(!poly.contains(10, 20));
		assertTrue(!poly.contains(50, 20));
		assertTrue(!poly.contains(10, 10));
		assertTrue(poly.contains(25, 25));

		// try some more complicated figures, in order to test particular
		// cases of the algorithm
		points = new Point2D[6];
		points[0] = new Point2D(40, 70);
		points[1] = new Point2D(40, 50);
		points[2] = new Point2D(20, 50);
		points[3] = new Point2D(60, 10);
		points[4] = new Point2D(60, 30);
		points[5] = new Point2D(80, 30);
		poly = new SimplePolygon2D(points);
		
		// classic case
		assertTrue(poly.contains(60, 40));
		// problematic case
		assertTrue(poly.contains(50, 40));

		points = new Point2D[8];
		points[0] = new Point2D(10, 60);
		points[1] = new Point2D(10, 40);
		points[2] = new Point2D(20, 50);
		points[3] = new Point2D(20, 20);
		points[4] = new Point2D(10, 30);
		points[5] = new Point2D(10, 10);
		points[6] = new Point2D(40, 10);
		points[7] = new Point2D(40, 60);
		poly = new SimplePolygon2D(points);
		
		// classic cases
		assertTrue(poly.contains(15, 15));
		assertTrue(poly.contains(25, 40));
		assertTrue(!poly.contains(15, 37));
		
		// problematic cases
		assertTrue(poly.contains(30, 35));
		assertTrue(!poly.contains(10, 35));
		assertTrue(!poly.contains(5, 35));		
	}

	public void testContainsPoint2D_CW() {
		// create ring in CW orientation
		LinearRing2D ring2 = new LinearRing2D(new Point2D[]{
				new Point2D(13, 13), 
				new Point2D(13, 17),
				new Point2D(17, 17),
				new Point2D(17, 13) });
		
		SimplePolygon2D polygon = new SimplePolygon2D(ring2);
		
		Point2D pOut = new Point2D(14, 16);
		assertFalse(polygon.contains(pOut));
		
		Point2D pIn1 = new Point2D(21, 20);
		assertTrue(polygon.contains(pIn1));
		
		Point2D pIn2 = new Point2D(11, 19);
		assertTrue(polygon.contains(pIn2));
	}
	
	public void testGetCentroiddoubledouble(){
		// start with a simple rectangle
		Point2D points[] = new Point2D[4];
		points[0] = new Point2D(20, 20);
		points[1] = new Point2D(40, 20);
		points[2] = new Point2D(40, 60);
		points[3] = new Point2D(20, 60);
		SimplePolygon2D poly = new SimplePolygon2D(points);

		Point2D centro = new Point2D(30, 40);
		assertTrue(centro.equals(poly.getCentroid()));
		
		
		// a cross centered around (15, 15), in reverse order
		poly = new SimplePolygon2D(new Point2D[]{
				new Point2D(10, 0),
				new Point2D(10, 10),
				new Point2D(0, 10),
				new Point2D(0, 20),
				new Point2D(10, 20),
				new Point2D(10, 30),
				new Point2D(20, 30),
				new Point2D(20, 20),
				new Point2D(30, 20),
				new Point2D(30, 10),
				new Point2D(20, 10),
				new Point2D(20, 0)});
		centro = new Point2D(15, 15);
		assertTrue(centro.equals(poly.getCentroid()));
	}
	
	public void testGetBoundingBox(){
		Point2D points[] = new Point2D[6];
		points[0] = new Point2D(40, 70);
		points[1] = new Point2D(40, 50);
		points[2] = new Point2D(20, 50);
		points[3] = new Point2D(60, 10);
		points[4] = new Point2D(60, 30);
		points[5] = new Point2D(80, 30);
		SimplePolygon2D poly = new SimplePolygon2D(points);
		
		Box2D box = new Box2D(20, 80, 10, 70);
		Box2D bounds = poly.getBoundingBox();
		assertTrue(box.equals(bounds));
	}
    
    public void testGetBuffer() {
    	SimplePolygon2D polygon =  new SimplePolygon2D(new Point2D[]{
                new Point2D(100, 100),
                new Point2D(150, 100),
                new Point2D(150, 150),
                new Point2D(100, 150)
        });
    	
    	CirculinearDomain2D buffer = polygon.getBuffer(10);
    	Boundary2D boundary = buffer.getBoundary();
    	assertEquals(1, boundary.getContinuousCurves().size());
    }
	
    public void testGetBuffer_MutipleVertices() {
    	SimplePolygon2D polygon =  new SimplePolygon2D(new Point2D[]{
                new Point2D(100, 100),
                new Point2D(150, 100),
                new Point2D(150, 100),
                new Point2D(150, 150),
                new Point2D(100, 150)
        });
    	
    	CirculinearDomain2D buffer = polygon.getBuffer(10);
    	Boundary2D boundary = buffer.getBoundary();
    	assertEquals(1, boundary.getContinuousCurves().size());
    }
	
    public void testClipBox2D_inside() {
    	SimplePolygon2D polygon =  new SimplePolygon2D(new Point2D[]{
                new Point2D(100, 100),
                new Point2D(150, 100),
                new Point2D(150, 150),
                new Point2D(100, 150)
        });
    	Box2D box = new Box2D(0, 500, 0, 500);
    	Polygon2D clipped = polygon.clip(box);
    	assertEquals(1, clipped.getRings().size());
    }
    
    public void testClipBox2D_intersect() {
    	SimplePolygon2D polygon =  new SimplePolygon2D(new Point2D[]{
                new Point2D(100, 100),
                new Point2D(300, 100),
                new Point2D(300, 300),
                new Point2D(100, 300)
        });
    	Box2D box = new Box2D(0, 200, 0, 200);
    	Polygon2D clipped = polygon.clip(box);
    	assertEquals(1, clipped.getRings().size());
    }
    
    public void testClipBox2D_intersectMulti() {
    	SimplePolygon2D polygon = SimplePolygon2D.create(new Point2D[]{
                new Point2D(40, 120),
                new Point2D(120, 120),
                new Point2D(120, 160),
                new Point2D(80, 160),
                new Point2D(80, 220),
                new Point2D(140, 220),
                new Point2D(140, 180),
                new Point2D(180, 180),
                new Point2D(180, 260),
                new Point2D(40, 260)  });
    	Box2D box = new Box2D(100, 200, 100, 200);
    	
    	Polygon2D clipped = polygon.clip(box);
    	assertEquals(2, clipped.getRings().size());
    }
    
	public void testCreate_Collection() {
		ArrayList<Point2D> array = new ArrayList<Point2D>(4);
		array.add(new Point2D(10, 10));
		array.add(new Point2D(20, 10));
		array.add(new Point2D(20, 20));
		array.add(new Point2D(10, 20));
		SimplePolygon2D ring = SimplePolygon2D.create(array);
		assertNotNull(ring);
	}
	
	public void testCreate_Array() {
		Point2D[] array = new Point2D[4];
		array[0] = new Point2D(10, 10);
		array[1] = new Point2D(20, 10);
		array[2] = new Point2D(20, 20);
		array[3] = new Point2D(10, 20);
		SimplePolygon2D ring = SimplePolygon2D.create(array);
		assertNotNull(ring);
	}
	
    public void testClone() {
        SimplePolygon2D polygon = new SimplePolygon2D(new Point2D[]{
                new Point2D(150, 50),
                new Point2D(150, 150),
                new Point2D(100, 100),
                new Point2D(50, 150),
                new Point2D(50, 50)
        });
        
        assertTrue(polygon.equals(polygon.clone()));
    }
}
