package moleculeMaker;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


public class ConnectionList 
{
	private HashMap<String, Element> element;
	private HashMap<String, Bond> bonds;
	private HashMap<String, Arrow> arrows;
	
//	private Element selectedElement;
//	private Bond selectedBond;
	private MoleculeComponent selected;
//	private Element dragging;
//	private Element moving;

	public ConnectionList()
	{
		element = new HashMap<String, Element>();
		bonds = new HashMap<String, Bond>();

//		selectedElement = null;
//		selectedBond = null;
		selected = null;

		arrows = new HashMap<String, Arrow>();
		
	}
	
	public void clearBonds()
	{
//		selectedElement = null;
//		selectedBond = null;
		selected = null;
//		dragging = null;
//		moving = null;

		bonds = new HashMap<String, Bond>();
	}
	
	public void clearElements()
	{
//		selectedElement = null;
//		selectedBond = null;
		selected = null;
//		dragging = null;
//		moving = null;
		bonds = new HashMap<String, Bond>();
		element = new HashMap<String, Element>();
	}
	
	private void removeBonds(MoleculeComponent selected2)
	{
		// Kudos to: http://joecode.blogspot.com/2004/10/hash-map-iteration.html
		// for the nifty iteration technique, and thanks to:
    	// http://stackoverflow.com/questions/1110404/remove-Elements-from-a-hashset-while-iterating
    	// for pointing out, I needed to use i.remove() instead of Bond.remove(key);
		
		String tempKey = selected2.getKey().replace(" ", "");
		
		for (Iterator<String> i = bonds.keySet().iterator(); i.hasNext();)
		{
			String key = i.next();
			
		    if (key.contains(tempKey))
		    {
		    	i.remove(); 
		    }
		}
	}
	
	/**
	 * Add a MoleculeComponent (element, bond, or arrow) to the list
	 * @param e The MoleculeComponent to add to the list
	 */
	public void add(MoleculeComponent e)
	{
		if (e.getClass() == Element.class) {
			element.put(e.getKey(), (Element) e);
		}
		else if (e.getClass() == Bond.class) {
			bonds.put(e.getKey(), (Bond) e);
		}
		else if (e.getClass() == Arrow.class) {
			arrows.put(e.getKey(), (Arrow) e);
		}
		
	}
	
//	public void add(MoleculeComponent e)
//	{
//		if (e.getClass() == Element.class) {
//			element.put(e.getKey(), (Element) e);
//		}
//		else if (e.getClass() == Bond.class) {
//			// Kudos to: http://joecode.blogspot.com/2004/10/hash-map-iteration.html
//			// for the nifty iteration technique, and thanks to:
//	    	// http://stackoverflow.com/questions/1110404/remove-ElementImproveds-from-a-hashset-while-iterating
//	    	// for pointing out, I needed to use i.remove() instead of BondImproved.remove(key);
//			bonds.put(e.getKey(), (Bond) e);
////			String tempKey = e.getKey().replace(" ", "");
////			
////			for (Iterator<String> i = bonds.keySet().iterator(); i.hasNext();) {
////				String key = i.next();
////				
////			    if (key.contains(tempKey)) {
////			    	i.remove(); 
////			    }
////			}
//		}
//		else if (e.getClass() == Arrow.class) {
//			arrows.put(e.getKey(), (Arrow) e);
//		}
//		
//	}
	
	public void remove(MoleculeComponent e)
	{
		if (e == null) { return; }
		
		if (e.getClass() == Element.class) {
			element.remove(e.getKey());
		}
		else if (e.getClass() == Bond.class) {
			bonds.remove(e.getKey());
		}
		else if (e.getClass() == Arrow.class) {
			arrows.remove(e.getKey());
		}
		
//		element.remove(e.getKey());
	}

//	public void addBond(Element e1, Element e2)
//	{
//		if (e1 == null || e2 == null) {
//			System.out.println("*** BondImproveding failed. Null is not allowed!");
//			return;
//		}
//		
//		if (e1.getKey().equals(e2.getKey())) {
//			System.out.println("*** Cannot BondImproved with the same point!");
//			return;
//		}
//		
//		
//		String BondImprovedKey = Bond.getKey(e1, e2);
//		
//		if(!bonds.containsKey(BondImprovedKey)) {
//			Bond temp = new Bond(e1, e2);
//			System.out.println("Temp BondImproved is: " + temp);
//			System.out.println("\tTemp BondImproved's BondImproveder is: " + temp.getConnector());
//			System.out.println("\tTemp BondImproved's BondImprovedee is: " + temp.getConnectee());
//			bonds.put(BondImprovedKey, temp);
//		}
//		else {
//			System.out.println("This BondImproved already exists!");
//		}
//		
//	}

	public Element[] getCoordinates()
	{
		// Return null if there are no Elements in map
		if (element.keySet().size() == 0) { return null; }
		
		Element[] temp = new Element[element.keySet().size()];
		
		int i = 0;
		for (Element e : element.values())
		{
			temp[i] = e;
			i++;
		}
		
		return temp;
	}

	public boolean hasElements()
	{
		return element.keySet().size() > 0;
	}
	
//	/**
//	 * Add an ElementImproved to the list by creating a new ElementImproved at the specified coordinates
//	 * @param x The X coordinate of the ElementImproved
//	 * @param y The Y coordinate of the ElementImproved
//	 */
//	public void addElement(int x, int y)
//	{
//		Element e = new Element(x, y);
//		addElement(e);
//	}
//	

	public void setSelected(MoleculeComponent e)
	{
		if (e == null) // If the incoming Element doesn't exist...
		{
			return;
		}
		if(selectedBond != null)
		{
			bonds.get(selectedBond.getKey()).setSelected(false);
			selectedBond = null;
		}
		if(selectedArrow != null)
		{
			arrows.get(selectedArrow.getKey()).setSelected(false);
			selectedArrow = null;
		}
		
		selectedElement = element.get(e.getKey()); // set selected to the newest Element selected
		element.get(selectedElement.getKey()).setSelected(true); // set the internal selection flag to true
	}
	
	public void setSelected(Bond b)
	{
		if (b == null) // If the incoming Bond doesn't exist...
		{
			if (selectedBond != null) // And there is already a selected Bond...
			{
				// ... clear that selected Bond
				// (this will be used when the user wishes to no longer select
				// anything, and clicks an empty area of the grid.
				element.get(selectedBond.getKey()).setSelected(false);
				selectedBond = null;
			}
			return;
		}
		
		if (element.get(b.getKey()) == null) // if the Bond is not in the map
		{
			selectedBond = null;
			return;
		}
		if(selectedElement != null)
		{
			element.get(selectedElement.getKey()).setSelected(false);
			selectedElement = null;
		}
		if(selectedBond != null)
		{
			bonds.get(selectedBond.getKey()).setSelected(false);
		}
		if(selectedArrow != null)
		{
			arrows.get(selectedArrow.getKey()).setSelected(false);
			selectedArrow = null;
		}
		selectedBond = bonds.get(b.getKey());
		bonds.get(selectedBond.getKey()).setSelected(true);
	}
	
	public void setSelected(Arrow a)
<<<<<<< HEAD
<<<<<<< HEAD
=======
=======
>>>>>>> 98a5899f2f1d4eb0edcdf2afc3d59f67cbfb8bb4
	{
		if (a == null) // If the incoming Bond doesn't exist...
		{
			if (selectedBond != null) // And there is already a selected Bond...
			{
				// ... clear that selected Bond
				// (this will be used when the user wishes to no longer select
				// anything, and clicks an empty area of the grid.
				element.get(selectedBond.getKey()).setSelected(false);
				selectedBond = null;
			}
			return;
		}
		
		if (element.get(a.getKey()) == null) // if the Arrow is not in the map
		{
			selectedArrow = null;
			return;
		}
		if(selectedElement != null)
		{
			element.get(selectedElement.getKey()).setSelected(false);
			selectedElement = null;
		}
		if(selectedBond != null)
		{
			bonds.get(selectedBond.getKey()).setSelected(false);
		}
		if(selectedArrow != null)
		{
			arrows.get(selectedArrow.getKey()).setSelected(false);
			selectedArrow = null;
		}
		selectedArrow = arrows.get(a.getKey());
		arrows.get(selectedArrow.getKey()).setSelected(true);
	}
	
	
	/**
	 * Clears the selected Element and also removes it from map the Elements.
	 */
	public void removeSelectedElement()
>>>>>>> 98a5899f2f1d4eb0edcdf2afc3d59f67cbfb8bb4
	{
		if (a == null) // If the incoming Bond doesn't exist...
		{
			if (selectedBond != null) // And there is already a selected Bond...
			{
				// ... clear that selected Bond
				// (this will be used when the user wishes to no longer select
				// anything, and clicks an empty area of the grid.
				element.get(selectedBond.getKey()).setSelected(false);
				selectedBond = null;
			}
			return;
		}
		
		if (element.get(a.getKey()) == null) // if the Arrow is not in the map
		{
			selectedArrow = null;
			return;
		}
		if(selectedElement != null)
		{
			element.get(selectedElement.getKey()).setSelected(false);
			selectedElement = null;
		}
		if(selectedBond != null)
		{
			bonds.get(selectedBond.getKey()).setSelected(false);
		}
		if(selectedArrow != null)
		{
			arrows.get(selectedArrow.getKey()).setSelected(false);
			selectedArrow = null;
		}
		selectedArrow = arrows.get(a.getKey());
		arrows.get(selectedArrow.getKey()).setSelected(true);
	}
	
>>>>>>> 98a5899f2f1d4eb0edcdf2afc3d59f67cbfb8bb4
	
	/**
	 * Clears the selected Element and also removes it from map the Elements.
	 */
	public void removeSelected()
	{
		if (selected == null) { return; }
		
		removeBonds(selected);
		element.remove(selected.getKey());
		selected = null;
		
	}
	
	public MoleculeComponent getSelected()
	{
		return (MoleculeComponent) selected;
	}
//	
//	public void setDragging(MoleculeComponent e)
//	{
//		dragging = e;
//	}
//	
//	public Element getDragging()
//	{
//		return dragging;
//	}
	
	public boolean isSelected(MoleculeComponent e)
	{
		if (e == null)
			return false;
		
		if (selected.getKey().equals(e.getKey()))
			return true;

		return false;
	}
	
	public String toString()
	{
		return "Element List: " + element.toString();
	}
	
	public Element getElementAt(int i, int j) {
		String key = "" + i + "," + j;
		return getElementAt(key);
	}
	
	public Element getElementAt(String key)
	{
		return element.get(key);
	}
	
//	public Element getMoving() {
//		return moving;
//	}
//
//	public void setMoving(MoleculeComponent moving) {
//		this.moving = moving;
//	}
	
	public ArrayList<Bond> getBonds()
	{
		return new ArrayList<Bond>(bonds.values());
	}

	public void setBonds(HashMap<String, Bond> Bonds) {
		this.bonds = Bonds;
	}
	
	public void setArrows(HashMap<String, Arrow> arrows) {
		this.arrows = arrows;
	}

	public HashMap<String, Element> getMap() {
		return element;
	}
	
	public HashMap<String, Arrow> getArrowHash() {
		return arrows;
	}

	public HashMap<String, Bond> getBondHash() {
		return bonds;
	}
	
}