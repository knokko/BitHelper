# BitHelper
A java library for compactly storing data

# Archived
This was my first (de)serialization library and has served me well, but it should be replaced because of its limitations (it is not as compact as it could be, and the implementation is too fixed). Currently, only my custom items minecraft plugin is still using this, so I just copied the code of this repository to the
repository of the plug-in. I am currently working on an improved version of this library, but in Rust (I might port it to Java or Kotlin someday): https://github.com/knokko/bit-encoding-rs

# Description
This library is made to store data as effectively as possible. For instance, storing 11 booleans only requires 2 bytes.
Instances of BitOutput can be used to save data and instances of BitInput can be used to load data.
The BitHelper class contains several utility methods (many are just copied from ByteBuffer, but public instead of package-private).

A simple example of how to use BitOutput and BitInput:

BitOutput output = new BitOutputStream(new FileOutputStream(new File("example.data")));
output.addBoolean(true);
output.addBoolean(false);
output.addInt(4356);
output.addJavaString("test");
output.addBoolean(true);
output.terminate();

BitInput input = new BitInputStream(new FileInputStream(new File("example.data")));
System.out.println(input.readBoolean());
System.out.println(input.readBoolean());
System.out.println(input.readInt());
System.out.println(input.readJavaString());
System.out.println(input.readBoolean());
input.terminate();

You could also use

BooleanArrayBitOutput output = new BooleanArrayBitOutput();
...

BitInput input = new BooleanArrayBitInput(output.getBooleans());
...

Or the same story with ByteArrayBitOutput and ByteArrayBitInput


This library could contain bugs, but I don't think it does.

The methods addNumber() and readNumber() can be confusing, so read carefully before using those (or avoid them).
