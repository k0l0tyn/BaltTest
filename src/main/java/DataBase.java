import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class DataBase {

	private int groupIdIterator = 1;
	private HashMap<Integer, Group> groupsPool;
	private HashMap<String, HashMap<Integer, Integer>> unitsPool; // unit,index,groupID
	private final Comparator<ArrayList<Line>> arraysSizeComparator = new Comparator<ArrayList<Line>>() {

		@Override
		public int compare(ArrayList<Line> first, ArrayList<Line> second) {
			return -(first.size() - second.size());
		}
	};

	public DataBase() {
		groupsPool = new HashMap<>();
		unitsPool = new HashMap<>();
	}

	public void add(Line line) {
		ArrayList<Integer> boundGroup = new ArrayList<Integer>();
		for (int i = 0; i < line.getUnits().size(); i++) {
			if (unitsPool.get(line.getUnits().get(i)) != null && unitsPool.get(line.getUnits().get(i)).get(i) != null) {
				if (!boundGroup.contains(unitsPool.get(line.getUnits().get(i)).get(i))) {
					boundGroup.add(unitsPool.get(line.getUnits().get(i)).get(i));
				}
			}
		}
		if (boundGroup.size() == 0) {
			groupsPool.put(groupIdIterator, new Group(line));
			for (int i = 0; i < line.getUnits().size(); i++) {
				HashMap<Integer, Integer> buf = new HashMap<>();
				buf.put(i, groupIdIterator);
				unitsPool.put(line.getUnits().get(i), buf);
			}
			groupIdIterator++;
		} else {
			groupsPool.get(boundGroup.get(0)).add(line);
			if (boundGroup.size() > 1) {
				for (int i = 1; i < boundGroup.size(); i++) {
					// merge units
					ArrayList<ArrayList<String>> exGroupUnit = groupsPool.get(boundGroup.get(i)).getUnits();
					for (int j = 0; j < exGroupUnit.size(); j++) {
						for (int k = 0; k < exGroupUnit.get(j).size(); k++) {
							if (unitsPool.get(exGroupUnit.get(j).get(k)) == null) {
								HashMap<Integer, Integer> newUnitHM = new HashMap<>();
								unitsPool.put(exGroupUnit.get(j).get(k), newUnitHM);
							}
							unitsPool.get(exGroupUnit.get(j).get(k)).put(j, boundGroup.get(0));
						}
					}
					// merge groups
					groupsPool.get(boundGroup.get(0)).consumeGroup(groupsPool.get(boundGroup.get(i)));
					groupsPool.remove(boundGroup.get(i));
				}
			}
		}

	}

	public ArrayList<ArrayList<Line>> getGroups() {
		System.out.println("Начато формирование списка");
		ArrayList<ArrayList<Line>> buf = new ArrayList<ArrayList<Line>>();
		for (Group group : groupsPool.values()) {
			group.removeEquals();
			if (group.getLines().size() > 1) {
				buf.add(group.getLines());
			}
		}
		System.out.println(buf.size());
		Collections.sort(buf, arraysSizeComparator);
		return buf;
	}

}
