package com.retrolaza.game.scenery;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jayway.jsonpath.JsonPath;
import com.retrolaza.game.scenery.drawable.Brick;
import com.retrolaza.game.scenery.drawable.BrickRow;
import com.retrolaza.game.scenery.drawable.Scenery;

public class SceneryParser {
	
	@SuppressWarnings("unchecked")
	public static Scenery load(String path) throws IOException {
		String s = new String(Files.readAllBytes(Paths.get(path)), "UTF-8");
		Map<Integer, BrickRow> rows = new HashMap<>();
		String background = JsonPath.read(s, "$.background");
		List<Object> list = JsonPath.read(s, "$.rows[*]");
		list.forEach(l -> {
			Map<Object, Object> m = (Map<Object, Object>) l;
			BrickRow row = new BrickRow((Integer) m.get("row"));
			List<Map<String, Integer>> bl = (List<Map<String, Integer>>) m.get("bricks");
			bl.forEach(b -> {
				Brick brick = new Brick(b.get("x"), (row.getRow() + 2) * 40, b.get("lives"));
				row.addBrick(brick);
			});
			rows.put(row.getRow(), row);
		});
		return new Scenery(rows, background);
	}
	
}
