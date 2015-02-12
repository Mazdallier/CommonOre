package com.genuineflix.data.primitives;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.lang.reflect.Type;

import net.minecraft.nbt.NBTTagString;

import org.apache.commons.lang3.StringEscapeUtils;

import com.genuineflix.data.AbstractData;
import com.genuineflix.data.IData;
import com.genuineflix.data.SizeLimit;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;

public class DataString extends AbstractData<String> {

	public static final String NAME = "STRING";
	public static final long SIZE = 8;
	public static final byte TYPE = 8;
	private String value = "";

	public DataString() {}

	public DataString(final String value) {
		this.value = value;
		if (value == null)
			throw new IllegalArgumentException("Null value not allowed");
	}

	@Override
	public String value() {
		return value;
	}

	@Override
	public void read(final DataInput in, final int depth, final SizeLimit limit) throws IOException {
		value = in.readUTF();
		limit.assertLimit(DataString.SIZE * value.length());
	}

	@Override
	public void write(final DataOutput out) throws IOException {
		out.writeUTF(value);
	}

	@Override
	public byte getTypeByte() {
		return DataString.TYPE;
	}

	@Override
	public String getTypeName() {
		return NAME;
	}

	@Override
	public String toString() {
		return StringEscapeUtils.escapeJson(value);
	}

	@Override
	public DataString copy() {
		return new DataString(value);
	}

	@Override
	public NBTTagString toNBT() {
		return new NBTTagString(value);
	}

	@Override
	public boolean equals(final Object obj) {
		if (super.equals(obj))
			return true;
		if (obj instanceof IData)
			return value().equals(((IData) obj).value());
		return value().equals(obj);
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}

	@Override
	public JsonPrimitive serialize(final IData<String> src, final Type typeOfSrc, final JsonSerializationContext context) {
		return new JsonPrimitive(src.value());
	}

	@Override
	public DataString deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
		return new DataString(json.getAsString());
	}
}
