package com.retrolaza.game.scenery;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jayway.jsonpath.JsonPath;
import com.retrolaza.game.scenery.data.Brick;
import com.retrolaza.game.scenery.data.BrickRow;

public class SceneryParser {
	
private List<BrickRow> rows;
	
	@SuppressWarnings("unchecked")
	public SceneryParser(String path) throws IOException {
		String s = new String(Files.readAllBytes(Paths.get(path)), "UTF-8");
		rows = new ArrayList<>();
		List<Object> list = JsonPath.read(s, "$.rows[*]");
		list.forEach(l -> {
			Map<Object, Object> m = (Map<Object, Object>) l;
			BrickRow row = new BrickRow((Integer) m.get("row"));
			List<Map<String, Integer>> bl = (List<Map<String, Integer>>) m.get("bricks");
			bl.forEach(b -> {
				Brick brick = new Brick(b.get("x"), b.get("lives"));
				row.addBrick(brick);
			});
			rows.add(row);
		});
	}
	
	public List<BrickRow> getRows() {
		return this.rows;
	}
	
}
