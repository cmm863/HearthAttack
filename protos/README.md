What are protobufs?
-------------------
The documentation is available here: https://developers.google.com/protocol-buffers/docs/overview

How do I use them?
------------------
In general, you make a file of the form:
```
message Person {
  required string name = 1;
  required int32 id = 2;
  optional string email = 3;

  enum PhoneType {
    MOBILE = 0;
    HOME = 1;
    WORK = 2;
  }

  message PhoneNumber {
    required string number = 1;
    optional PhoneType type = 2 [default = HOME];
  }

  repeated PhoneNumber phone = 4;
}

message AddressBook {
  repeated Person person = 1;
}
```

Then compile it with protoc: http://manpages.ubuntu.com/manpages/jaunty/en/man1/protoc.1.html
Into the language of your choice: https://code.google.com/p/protobuf/wiki/ThirdPartyAddOns

With one of:
```
protoc --lua_out=./ addressbook.proto
protoc --cpp_out=./ addressbook.proto
protoc --java_out=./ addressbook.proto
protoc --python_out=./ addressbook.proto
```

Or more generally:
```
protoc -I=$SRC_DIR --python_out=$DST_DIR $SRC_DIR/addressbook.proto
```

Then you read in the file and encode a message based on that prototype:
```
import addressbook_pb2

person = addressbook_pb2.Person()
person.id = 1234
person.name = "John Doe"
person.email = "jdoe@example.com"
phone = person.phone.add()
phone.number = "555-4321"
phone.type = addressbook_pb2.Person.HOME

msg = person.SerializeToString()
```

or decode and access:

```
import addressbook_pb2

person = addressbook_pb2.Person()

input = msg

person.ParseFromString(msg)
new_var = person.id
```