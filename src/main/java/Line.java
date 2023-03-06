import java.util.ArrayList;

public class Line {
	private ArrayList<String> units;

	public Line() {
		units = new ArrayList<String>();
	}

	public void add(String newUnit) {
		units.add(newUnit);
	}

	public int getSize() {
		return units.size();
	}

	public ArrayList<String> getUnits() {
		return units;
	}

	public void clear() {
		units = new ArrayList<String>();
	}

	public boolean isCleared() {
		if (units.size() == 0) {
			return true;
		}
		return false;
	}

	public String getString() {
		String s = "";
		for (String buf : units) {
			s += ";\"" + buf + "\"";
		}
		s = s.substring(1);
		return s;
	}

	public boolean equals(Line line) {
		if (line.getSize() != getSize()) {
			return false;
		} else {
			for(int i = 0;i<getSize();i++) {
				if(!line.getUnits().get(i).equals(units.get(i))) {
					return false;
				}
			}
		}
		return true;
	}
}
