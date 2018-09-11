/* 
 * The MIT License
 *
 * Copyright 2018 20182191.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package nl.knokko.util.bits;

public abstract class BitOutput {
	
	public static byte getRequiredBits(long number){
		if(number < 0)
			number = -(number + 1);
		long l = 1;
		byte b = 0;
		while(l <= number){//recently changed the < to <=
			l *= 2;
			b++;
		}
		return b;
	}
	
	public abstract void addDirectBoolean(boolean value);
	
	public abstract void addDirectByte(byte value);
	
	public abstract void ensureExtraCapacityCapacity(int booleans);
	
	public abstract void terminate();
	
	public void addDirectBooleans(boolean... bools){
		for(boolean bool : bools)
			addDirectBoolean(bool);
	}
	
	public void addDirectBytes(byte... bytes){
		for(byte b : bytes)
			addDirectByte(b);
	}
	
	public void addDirectChar(char value){
		addDirectBytes(BitHelper.char0(value), BitHelper.char1(value));
	}
	
	public void addDirectShort(short value){
		addDirectBytes(BitHelper.short0(value), BitHelper.short1(value));
	}
	
	public void addDirectInt(int value){
		addDirectBytes(BitHelper.int0(value), BitHelper.int1(value), BitHelper.int2(value), BitHelper.int3(value));
	}
	
	public void addDirectFloat(float value){
		addDirectInt(Float.floatToRawIntBits(value));
	}
	
	public void addDirectLong(long value){
		addDirectBytes(BitHelper.long0(value), BitHelper.long1(value), BitHelper.long2(value), BitHelper.long3(value), BitHelper.long4(value), BitHelper.long5(value), BitHelper.long6(value), BitHelper.long7(value));
	}
	
	public void addDirectDouble(double value){
		addDirectLong(Double.doubleToRawLongBits(value));
	}
	
	public void addBoolean(boolean value){
		ensureExtraCapacityCapacity(1);
		addDirectBoolean(value);
	}
	
	public void addBooleans(boolean... bools){
		ensureExtraCapacityCapacity(bools.length);
		for(boolean bool : bools)
			addDirectBoolean(bool);
	}
	
	public void addBooleanArray(boolean[] value){
		ensureExtraCapacityCapacity(32 + value.length);
		addDirectInt(value.length);
		addDirectBooleans(value);
	}
	
	public void addByte(byte value){
		ensureExtraCapacityCapacity(8);
		addDirectByte(value);
	}
	
	public void addBytes(byte... bytes){
		ensureExtraCapacityCapacity(bytes.length * 8);
		for(byte b : bytes)
			addDirectByte(b);
	}
	
	public void addBytes(byte[] bytes, int startIndex, int amount){
		ensureExtraCapacityCapacity(amount * 8);
		for(int i = 0; i < amount; i++)
			addDirectByte(bytes[startIndex + i]);
	}
	
	public void addByteArray(byte[] value){
		ensureExtraCapacityCapacity(32 + value.length * 8);
		addDirectInt(value.length);
		addDirectBytes(value);
	}
	
	public void addShort(short value){
		addBytes(BitHelper.short0(value), BitHelper.short1(value));
	}
	
	public void addShorts(short... shorts){
		ensureExtraCapacityCapacity(shorts.length * 16);
		for(short s : shorts)
			addDirectShort(s);
	}
	
	public void addShorts(short[] shorts, int startIndex, int amount){
		ensureExtraCapacityCapacity(16 * amount);
		for(int i = 0; i < amount; i++)
			addDirectShort(shorts[startIndex + i]);
	}
	
	public void addShortArray(short[] value){
		ensureExtraCapacityCapacity(32 + value.length * 16);
		addDirectInt(value.length);
		for(short s : value)
			addDirectShort(s);
	}
	
	public void addChar(char value){
		addBytes(BitHelper.char0(value), BitHelper.char1(value));
	}
	
	public void addChars(char... chars){
		ensureExtraCapacityCapacity(chars.length * 16);
		for(char c : chars)
			addDirectChar(c);
	}
	
	public void addChars(char[] chars, int startIndex, int amount){
		ensureExtraCapacityCapacity(amount * 16);
		for(int i = 0; i < amount; i++)
			addDirectChar(chars[startIndex + i]);
	}
	
	public void addCharArray(char[] value){
		ensureExtraCapacityCapacity(32 + value.length * 16);
		addDirectInt(value.length);
		for(char c : value)
			addDirectChar(c);
	}
	
	public void addInt(int value){
		addBytes(BitHelper.int0(value), BitHelper.int1(value), BitHelper.int2(value), BitHelper.int3(value));
	}
	
	public void addInts(int... ints){
		ensureExtraCapacityCapacity(ints.length * 32);
		for(int i : ints)
			addDirectInt(i);
	}
	
	public void addInts(int[] ints, int startIndex, int amount){
		ensureExtraCapacityCapacity(amount * 32);
		for(int i = 0; i < amount; i++)
			addDirectInt(ints[startIndex + i]);
	}
	
	public void addIntArray(int[] value){
		ensureExtraCapacityCapacity(32 + value.length * 32);
		addDirectInt(value.length);
		for(int i : value)
			addDirectInt(i);
	}
	
	public void addFloat(float value){
		addInt(Float.floatToRawIntBits(value));
	}
	
	public void addFloats(float... floats){
		ensureExtraCapacityCapacity(floats.length * 32);
		for(float f : floats)
			addDirectFloat(f);
	}
	
	public void addFloats(float[] floats, int startIndex, int amount){
		ensureExtraCapacityCapacity(32 * amount);
		for(int i = 0; i < amount; i++)
			addDirectFloat(floats[startIndex + i]);
	}
	
	public void addFloatArray(float[] value){
		ensureExtraCapacityCapacity(32 + value.length * 32);
		addDirectInt(value.length);
		for(float f : value)
			addDirectFloat(f);
	}
	
	public void addLong(long value){
		addBytes(BitHelper.long0(value), BitHelper.long1(value), BitHelper.long2(value), BitHelper.long3(value), BitHelper.long4(value), BitHelper.long5(value), BitHelper.long6(value), BitHelper.long7(value));
	}
	
	public void addLongs(long... longs){
		ensureExtraCapacityCapacity(64 * longs.length);
		for(long l : longs)
			addDirectLong(l);
	}
	
	public void addLongs(long[] longs, int startIndex, int amount){
		ensureExtraCapacityCapacity(64 * amount);
		for(int i = 0; i < amount; i++)
			addDirectLong(longs[startIndex + 1]);
	}
	
	public void addLongArray(long[] value){
		ensureExtraCapacityCapacity(32 + value.length * 64);
		addDirectInt(value.length);
		for(long l : value)
			addDirectLong(l);
	}
	
	public void addDouble(double value){
		addLong(Double.doubleToRawLongBits(value));
	}
	
	public void addDoubles(double... doubles){
		ensureExtraCapacityCapacity(64 * doubles.length);
		for(double d : doubles)
			addDirectDouble(d);
	}
	
	public void addDoubles(double[] doubles, int startIndex, int amount){
		ensureExtraCapacityCapacity(64 * amount);
		for(int i = 0; i < amount; i++)
			addDirectDouble(doubles[startIndex + i]);
	}
	
	public void addDoubleArray(double[] value){
		ensureExtraCapacityCapacity(32 + value.length * 64);
		addDirectInt(value.length);
		for(double d : value)
			addDirectDouble(d);
	}
	
	public void addNumber(long number, byte bitCount, boolean allowNegative){
		addBooleans(BitHelper.numberToBinary(number, bitCount, allowNegative));
	}
	
	public void addNumber(long number, boolean allowNegative){
		if(!allowNegative && number < 0)
			throw new IllegalArgumentException("Number (" + number + ") can't be negative!");
		byte bitCount = getRequiredBits(number);
		if(allowNegative)
			bitCount++;
		ensureExtraCapacityCapacity(6 + bitCount);
		addDirectBooleans(BitHelper.numberToBinary(bitCount, (byte) 6, false));
		addDirectBooleans(BitHelper.numberToBinary(number, bitCount, allowNegative));
	}
	
	public void addJavaString(String string){
		/*
		ensureExtraCapacityCapacity(32 + value.length() * 16);
		addDirectInt(value.length());
		for(int i = 0; i < value.length(); i++)
			addDirectChar(value.charAt(i));
			*/
		if(string == null){
			addInt(-1);
			return;
		}
		char max = 1;
		for(int i = 0; i < string.length(); i++){
			char c = string.charAt(i);
			if(c > max)
				max = c;
		}
		//byte bitCount = Maths.log2Up(max);
		byte bitCount = getRequiredBits(max);
		
		//maximum is 2^16 - 1 --> maximum bitCount is 16
		//ensureCapacity(writeIndex + 32 + 4 + bitCount * string.length());
		ensureExtraCapacityCapacity(32 + 4 + bitCount * string.length());
		addInt(string.length());
		addNumber(bitCount - 1, (byte) 4, false);
		for(int i = 0; i < string.length(); i++)
			addNumber(string.charAt(i), bitCount, false);
	}
}
