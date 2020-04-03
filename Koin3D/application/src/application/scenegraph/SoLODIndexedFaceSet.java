/**
 * 
 */
package application.scenegraph;

import jscenegraph.database.inventor.SbBox3f;
import jscenegraph.database.inventor.SbVec3f;
import jscenegraph.database.inventor.actions.SoGLRenderAction;
import jscenegraph.database.inventor.nodes.SoIndexedFaceSet;

/**
 * @author Yves Boyadjian
 *
 */
public class SoLODIndexedFaceSet extends SoIndexedFaceSet {

	private final SbVec3f referencePoint;
	
	public float maxDistance;
	
	private final SbBox3f box = new SbBox3f();
	
	private final SbVec3f center = new SbVec3f();
	
	private final SbVec3f dummy = new SbVec3f(); //SINGLE_THREAD
	
	public SoLODIndexedFaceSet(SbVec3f referencePoint) {
		this.referencePoint = referencePoint;
	}
	
	public void GLRender(SoGLRenderAction action)
	{		
		getBBox(action, box, center);
		
		if( box.intersect(referencePoint)) {		
			super.GLRender(action);
		}
		else {
			SbVec3f closestPoint = box.getClosestExternalPoint(referencePoint);
			
			if( closestPoint.operator_minus(referencePoint,dummy).length() <= maxDistance ) {
				super.GLRender(action);				
			}
		}
	}			
}
