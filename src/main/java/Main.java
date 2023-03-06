import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class Main {
	private static final String DATA_PATH = "src/main/resources/InputData.txt";
	private static final int BUFFER_SIZE = 1024*32;

	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("Please enter the output file destination");
			return;
		}
		long timestamp = System.currentTimeMillis();
		long read = 0;// Delete
		DataBase pool = new DataBase();
		/*
		 * new BufferedInputStream(new GZIPInputStream( new URL(
		 * "https://github.com/PeacockTeam/new-job/releases/download/v1.0/lng-4.txt.gz")
		 * .openStream()))
		 */
		try (FileInputStream in = new FileInputStream(DATA_PATH);
				FileOutputStream out = new FileOutputStream(args[0])) {
			String buf = "";
			byte dataBuffer[] = new byte[BUFFER_SIZE];
			int bytesRead;
			while ((bytesRead = in.read(dataBuffer)) != -1) {
				read += BUFFER_SIZE;// Delete
				buf += new String(dataBuffer);
				String[] lines = buf.split("\n");
				buf = lines[lines.length - 1];
				for (int i = 0; i < lines.length - 1; i++) {
					Line line = parseLine(lines[i]);
					if (!line.isCleared()) {
						pool.add(line);
					}
				}
				System.out.println("Read: " + (float) (read / 83136404.0) * 100 + "%");
			}
			ArrayList<ArrayList<Line>> groups = pool.getGroups();
			out.write(("Получившиееся число групп с более чем одним элементом - " + groups.size() + "\n").getBytes());
			out.write(("Время работы составило: " + (double) (System.currentTimeMillis() - timestamp) / 1000
					+ " секунд\n").getBytes());
			for (int i = 0; i < groups.size(); i++) {
				out.write(("Группа " + (i + 1) + "\n\n").getBytes());
				for (Line line : groups.get(i)) {
					out.write((line.getString() + "\n\n").getBytes());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(
				"Время работы составило:" + (double) (System.currentTimeMillis() - timestamp) / 1000 + " секунд");
	}

	private static Line parseLine(String s) {
		Line line = new Line();
		String[] units = s.split(";");
		for (int i = 0; i < units.length; i++) {
			if (units[i].length() > 1) {
				String buf = units[i].substring(1, units[i].length() - 1);
				if (!Pattern.matches("^\\d", buf)) {
					line.add(buf);
				} else {
					line.clear();
					break;
				}
			}
		}
		return line;

	}
}
