import java.util.ArrayList;

public class Group {
	private ArrayList<Line> lines;
	private ArrayList<ArrayList<String>> units;

	public Group(Line line) {
		lines = new ArrayList<Line>();
		units = new ArrayList<ArrayList<String>>();
		add(line);
	}

	public boolean checkLine(Line line) {
		for (int i = 0; i < line.getUnits().size() && i < units.size(); i++) {
			if (units.get(i).contains(line.getUnits().get(i))) {
				return true;
			}
		}
		return false;
	}

	public void add(Line line) {
		lines.add(line);
		for (int i = 0; i < line.getUnits().size(); i++) {
			supplementGroups(i);
			units.get(i).add(line.getUnits().get(i));
		}
	}

	private void supplementGroups(int i) {
		if (i >= units.size()) {
			ArrayList<String> newIndex = new ArrayList<String>();
			units.add(newIndex);
		}
	}

	public void consumeGroup(Group g) {
		lines.addAll(g.getLines());
		for (int i = 0; i < g.getUnits().size(); i++) {
			supplementGroups(i);
			units.get(i).addAll(g.getUnits().get(i));
		}
	}

	public ArrayList<ArrayList<String>> getUnits() {
		return units;
	}

	public void removeEquals() {
		if (lines.size() > 1) {
			for (int i = 0; i < lines.size(); i++) {
				for (int j = i + 1; j < lines.size(); j++) {
					if (lines.get(i).equals(lines.get(j))) {
						lines.remove(j);
						j--;
					}
				}
			}
		}
	}

	public ArrayList<Line> getLines() {
		return lines;
	}
}
