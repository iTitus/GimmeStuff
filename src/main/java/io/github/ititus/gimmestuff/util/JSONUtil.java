package io.github.ititus.gimmestuff.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.apache.commons.lang3.StringUtils;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagByteArray;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagEnd;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;

public class JSONUtil {

	private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
	private static final JsonParser jsonParser = new JsonParser();

	public static String getJSONString(NBTTagCompound compound) {
		if (compound == null) {
			return "";
		}
		StringBuilder builder = new StringBuilder();
		getRawJSONStringFixed(builder, compound);
		JsonElement jsonElement = jsonParser.parse(builder.toString());
		return gson.toJson(jsonElement);
	}

	private static void getRawJSONStringFixed(StringBuilder builder, NBTBase nbt) {
		if (nbt == null) {
			builder.append("null");
		} else if (nbt instanceof NBTBase.NBTPrimitive) {
			builder.append(nbt.toString());
		} else if (nbt instanceof NBTTagString) {
			builder.append("\"" + StringUtils.replaceEach(((NBTTagString) nbt).getString(), new String[]{"\\", "\""}, new String[]{"\\\\", "\\\""}) + "\"");
		} else if (nbt instanceof NBTTagByteArray) {
			byte[] array = ((NBTTagByteArray) nbt).getByteArray();

			builder.append('[');
			for (int i = 0; i < array.length; i++) {
				byte b = array[i];
				if (i > 0) {
					builder.append(',');
				}
				builder.append(b).append('b');
			}
			builder.append(']');
		} else if (nbt instanceof NBTTagIntArray) {
			int[] array = ((NBTTagIntArray) nbt).getIntArray();

			builder.append('[');
			for (int i = 0; i < array.length; i++) {
				int j = array[i];
				if (i > 0) {
					builder.append(',');
				}
				builder.append(j);
			}
			builder.append(']');
		} else if (nbt instanceof NBTTagCompound) {
			NBTTagCompound compound = (NBTTagCompound) nbt;
			boolean first = true;

			builder.append('{');
			for (String key : compound.getKeySet()) {
				NBTBase nbtBase = compound.getTag(key);
				if (!(nbtBase instanceof NBTTagEnd)) {
					if (!first) {
						builder.append(',');
					}
					builder.append(key).append(':');
					getRawJSONStringFixed(builder, nbtBase);
					first = false;
				}
			}
			builder.append('}');
		} else if (nbt instanceof NBTTagList) {
			NBTTagList nbtTagList = (NBTTagList) nbt;
			boolean first = true;

			builder.append('[');
			for (int i = 0; i < nbtTagList.tagCount(); i++) {
				NBTBase nbtBase = nbtTagList.get(i);
				if (!(nbtBase instanceof NBTTagEnd)) {
					if (!first) {
						builder.append(',');
					}
					getRawJSONStringFixed(builder, nbtBase);
					first = false;
				}
			}
			builder.append(']');
		}
	}

}
