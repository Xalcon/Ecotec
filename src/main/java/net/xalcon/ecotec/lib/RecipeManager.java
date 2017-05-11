package net.xalcon.ecotec.lib;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.Collections;
import java.util.Iterator;
import java.util.stream.Stream;

public class RecipeManager
{
	public void initializeCrafting(Class<?> clazz, String path)
	{
		try
		{
			URI uri = clazz.getResource(path).toURI();
			Path resourcePath;
			if (uri.getScheme().equals("jar"))
				resourcePath = FileSystems.newFileSystem(uri, Collections.emptyMap()).getPath(path);
			else
				resourcePath = Paths.get(uri);
			Stream<Path> walk = Files.walk(resourcePath, Integer.MAX_VALUE);
			for (Iterator<Path> it = walk.iterator(); it.hasNext();)
			{
				System.out.println(it.next());
			}
		}
		catch (IOException | URISyntaxException e)
		{
			e.printStackTrace();
		}
	}
}
