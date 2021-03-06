// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: card.proto

package com.protos;

public final class CardProto {
  private CardProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  public interface CardOrBuilder extends
      // @@protoc_insertion_point(interface_extends:com.protos.Card)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>optional bool has_been_used = 1;</code>
     */
    boolean hasHasBeenUsed();
    /**
     * <code>optional bool has_been_used = 1;</code>
     */
    boolean getHasBeenUsed();

    /**
     * <code>optional bool in_hand = 2;</code>
     */
    boolean hasInHand();
    /**
     * <code>optional bool in_hand = 2;</code>
     */
    boolean getInHand();

    /**
     * <code>optional int32 id = 3;</code>
     */
    boolean hasId();
    /**
     * <code>optional int32 id = 3;</code>
     */
    int getId();

    /**
     * <code>required string name = 4;</code>
     */
    boolean hasName();
    /**
     * <code>required string name = 4;</code>
     */
    java.lang.String getName();
    /**
     * <code>required string name = 4;</code>
     */
    com.google.protobuf.ByteString
        getNameBytes();
  }
  /**
   * Protobuf type {@code com.protos.Card}
   */
  public static final class Card extends
      com.google.protobuf.GeneratedMessage implements
      // @@protoc_insertion_point(message_implements:com.protos.Card)
      CardOrBuilder {
    // Use Card.newBuilder() to construct.
    private Card(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
      super(builder);
      this.unknownFields = builder.getUnknownFields();
    }
    private Card(boolean noInit) { this.unknownFields = com.google.protobuf.UnknownFieldSet.getDefaultInstance(); }

    private static final Card defaultInstance;
    public static Card getDefaultInstance() {
      return defaultInstance;
    }

    public Card getDefaultInstanceForType() {
      return defaultInstance;
    }

    private final com.google.protobuf.UnknownFieldSet unknownFields;
    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
        getUnknownFields() {
      return this.unknownFields;
    }
    private Card(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      initFields();
      int mutable_bitField0_ = 0;
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
            case 8: {
              bitField0_ |= 0x00000001;
              hasBeenUsed_ = input.readBool();
              break;
            }
            case 16: {
              bitField0_ |= 0x00000002;
              inHand_ = input.readBool();
              break;
            }
            case 24: {
              bitField0_ |= 0x00000004;
              id_ = input.readInt32();
              break;
            }
            case 34: {
              com.google.protobuf.ByteString bs = input.readBytes();
              bitField0_ |= 0x00000008;
              name_ = bs;
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e.getMessage()).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.protos.CardProto.internal_static_com_protos_Card_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.protos.CardProto.internal_static_com_protos_Card_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.protos.CardProto.Card.class, com.protos.CardProto.Card.Builder.class);
    }

    public static com.google.protobuf.Parser<Card> PARSER =
        new com.google.protobuf.AbstractParser<Card>() {
      public Card parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new Card(input, extensionRegistry);
      }
    };

    @java.lang.Override
    public com.google.protobuf.Parser<Card> getParserForType() {
      return PARSER;
    }

    private int bitField0_;
    public static final int HAS_BEEN_USED_FIELD_NUMBER = 1;
    private boolean hasBeenUsed_;
    /**
     * <code>optional bool has_been_used = 1;</code>
     */
    public boolean hasHasBeenUsed() {
      return ((bitField0_ & 0x00000001) == 0x00000001);
    }
    /**
     * <code>optional bool has_been_used = 1;</code>
     */
    public boolean getHasBeenUsed() {
      return hasBeenUsed_;
    }

    public static final int IN_HAND_FIELD_NUMBER = 2;
    private boolean inHand_;
    /**
     * <code>optional bool in_hand = 2;</code>
     */
    public boolean hasInHand() {
      return ((bitField0_ & 0x00000002) == 0x00000002);
    }
    /**
     * <code>optional bool in_hand = 2;</code>
     */
    public boolean getInHand() {
      return inHand_;
    }

    public static final int ID_FIELD_NUMBER = 3;
    private int id_;
    /**
     * <code>optional int32 id = 3;</code>
     */
    public boolean hasId() {
      return ((bitField0_ & 0x00000004) == 0x00000004);
    }
    /**
     * <code>optional int32 id = 3;</code>
     */
    public int getId() {
      return id_;
    }

    public static final int NAME_FIELD_NUMBER = 4;
    private java.lang.Object name_;
    /**
     * <code>required string name = 4;</code>
     */
    public boolean hasName() {
      return ((bitField0_ & 0x00000008) == 0x00000008);
    }
    /**
     * <code>required string name = 4;</code>
     */
    public java.lang.String getName() {
      java.lang.Object ref = name_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        if (bs.isValidUtf8()) {
          name_ = s;
        }
        return s;
      }
    }
    /**
     * <code>required string name = 4;</code>
     */
    public com.google.protobuf.ByteString
        getNameBytes() {
      java.lang.Object ref = name_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        name_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    private void initFields() {
      hasBeenUsed_ = false;
      inHand_ = false;
      id_ = 0;
      name_ = "";
    }
    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      if (!hasName()) {
        memoizedIsInitialized = 0;
        return false;
      }
      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      getSerializedSize();
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        output.writeBool(1, hasBeenUsed_);
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        output.writeBool(2, inHand_);
      }
      if (((bitField0_ & 0x00000004) == 0x00000004)) {
        output.writeInt32(3, id_);
      }
      if (((bitField0_ & 0x00000008) == 0x00000008)) {
        output.writeBytes(4, getNameBytes());
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;

      size = 0;
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        size += com.google.protobuf.CodedOutputStream
          .computeBoolSize(1, hasBeenUsed_);
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        size += com.google.protobuf.CodedOutputStream
          .computeBoolSize(2, inHand_);
      }
      if (((bitField0_ & 0x00000004) == 0x00000004)) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(3, id_);
      }
      if (((bitField0_ & 0x00000008) == 0x00000008)) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(4, getNameBytes());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    private static final long serialVersionUID = 0L;
    @java.lang.Override
    protected java.lang.Object writeReplace()
        throws java.io.ObjectStreamException {
      return super.writeReplace();
    }

    public static com.protos.CardProto.Card parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.protos.CardProto.Card parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.protos.CardProto.Card parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.protos.CardProto.Card parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.protos.CardProto.Card parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static com.protos.CardProto.Card parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }
    public static com.protos.CardProto.Card parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input);
    }
    public static com.protos.CardProto.Card parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input, extensionRegistry);
    }
    public static com.protos.CardProto.Card parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static com.protos.CardProto.Card parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }

    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(com.protos.CardProto.Card prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code com.protos.Card}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:com.protos.Card)
        com.protos.CardProto.CardOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.protos.CardProto.internal_static_com_protos_Card_descriptor;
      }

      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.protos.CardProto.internal_static_com_protos_Card_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.protos.CardProto.Card.class, com.protos.CardProto.Card.Builder.class);
      }

      // Construct using com.protos.CardProto.Card.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessage.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
        }
      }
      private static Builder create() {
        return new Builder();
      }

      public Builder clear() {
        super.clear();
        hasBeenUsed_ = false;
        bitField0_ = (bitField0_ & ~0x00000001);
        inHand_ = false;
        bitField0_ = (bitField0_ & ~0x00000002);
        id_ = 0;
        bitField0_ = (bitField0_ & ~0x00000004);
        name_ = "";
        bitField0_ = (bitField0_ & ~0x00000008);
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(buildPartial());
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.protos.CardProto.internal_static_com_protos_Card_descriptor;
      }

      public com.protos.CardProto.Card getDefaultInstanceForType() {
        return com.protos.CardProto.Card.getDefaultInstance();
      }

      public com.protos.CardProto.Card build() {
        com.protos.CardProto.Card result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public com.protos.CardProto.Card buildPartial() {
        com.protos.CardProto.Card result = new com.protos.CardProto.Card(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
          to_bitField0_ |= 0x00000001;
        }
        result.hasBeenUsed_ = hasBeenUsed_;
        if (((from_bitField0_ & 0x00000002) == 0x00000002)) {
          to_bitField0_ |= 0x00000002;
        }
        result.inHand_ = inHand_;
        if (((from_bitField0_ & 0x00000004) == 0x00000004)) {
          to_bitField0_ |= 0x00000004;
        }
        result.id_ = id_;
        if (((from_bitField0_ & 0x00000008) == 0x00000008)) {
          to_bitField0_ |= 0x00000008;
        }
        result.name_ = name_;
        result.bitField0_ = to_bitField0_;
        onBuilt();
        return result;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof com.protos.CardProto.Card) {
          return mergeFrom((com.protos.CardProto.Card)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.protos.CardProto.Card other) {
        if (other == com.protos.CardProto.Card.getDefaultInstance()) return this;
        if (other.hasHasBeenUsed()) {
          setHasBeenUsed(other.getHasBeenUsed());
        }
        if (other.hasInHand()) {
          setInHand(other.getInHand());
        }
        if (other.hasId()) {
          setId(other.getId());
        }
        if (other.hasName()) {
          bitField0_ |= 0x00000008;
          name_ = other.name_;
          onChanged();
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public final boolean isInitialized() {
        if (!hasName()) {
          
          return false;
        }
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        com.protos.CardProto.Card parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (com.protos.CardProto.Card) e.getUnfinishedMessage();
          throw e;
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      private boolean hasBeenUsed_ ;
      /**
       * <code>optional bool has_been_used = 1;</code>
       */
      public boolean hasHasBeenUsed() {
        return ((bitField0_ & 0x00000001) == 0x00000001);
      }
      /**
       * <code>optional bool has_been_used = 1;</code>
       */
      public boolean getHasBeenUsed() {
        return hasBeenUsed_;
      }
      /**
       * <code>optional bool has_been_used = 1;</code>
       */
      public Builder setHasBeenUsed(boolean value) {
        bitField0_ |= 0x00000001;
        hasBeenUsed_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional bool has_been_used = 1;</code>
       */
      public Builder clearHasBeenUsed() {
        bitField0_ = (bitField0_ & ~0x00000001);
        hasBeenUsed_ = false;
        onChanged();
        return this;
      }

      private boolean inHand_ ;
      /**
       * <code>optional bool in_hand = 2;</code>
       */
      public boolean hasInHand() {
        return ((bitField0_ & 0x00000002) == 0x00000002);
      }
      /**
       * <code>optional bool in_hand = 2;</code>
       */
      public boolean getInHand() {
        return inHand_;
      }
      /**
       * <code>optional bool in_hand = 2;</code>
       */
      public Builder setInHand(boolean value) {
        bitField0_ |= 0x00000002;
        inHand_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional bool in_hand = 2;</code>
       */
      public Builder clearInHand() {
        bitField0_ = (bitField0_ & ~0x00000002);
        inHand_ = false;
        onChanged();
        return this;
      }

      private int id_ ;
      /**
       * <code>optional int32 id = 3;</code>
       */
      public boolean hasId() {
        return ((bitField0_ & 0x00000004) == 0x00000004);
      }
      /**
       * <code>optional int32 id = 3;</code>
       */
      public int getId() {
        return id_;
      }
      /**
       * <code>optional int32 id = 3;</code>
       */
      public Builder setId(int value) {
        bitField0_ |= 0x00000004;
        id_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional int32 id = 3;</code>
       */
      public Builder clearId() {
        bitField0_ = (bitField0_ & ~0x00000004);
        id_ = 0;
        onChanged();
        return this;
      }

      private java.lang.Object name_ = "";
      /**
       * <code>required string name = 4;</code>
       */
      public boolean hasName() {
        return ((bitField0_ & 0x00000008) == 0x00000008);
      }
      /**
       * <code>required string name = 4;</code>
       */
      public java.lang.String getName() {
        java.lang.Object ref = name_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          if (bs.isValidUtf8()) {
            name_ = s;
          }
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>required string name = 4;</code>
       */
      public com.google.protobuf.ByteString
          getNameBytes() {
        java.lang.Object ref = name_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          name_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>required string name = 4;</code>
       */
      public Builder setName(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000008;
        name_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required string name = 4;</code>
       */
      public Builder clearName() {
        bitField0_ = (bitField0_ & ~0x00000008);
        name_ = getDefaultInstance().getName();
        onChanged();
        return this;
      }
      /**
       * <code>required string name = 4;</code>
       */
      public Builder setNameBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000008;
        name_ = value;
        onChanged();
        return this;
      }

      // @@protoc_insertion_point(builder_scope:com.protos.Card)
    }

    static {
      defaultInstance = new Card(true);
      defaultInstance.initFields();
    }

    // @@protoc_insertion_point(class_scope:com.protos.Card)
  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_protos_Card_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_com_protos_Card_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\ncard.proto\022\ncom.protos\"H\n\004Card\022\025\n\rhas_" +
      "been_used\030\001 \001(\010\022\017\n\007in_hand\030\002 \001(\010\022\n\n\002id\030\003" +
      " \001(\005\022\014\n\004name\030\004 \002(\tB\027\n\ncom.protosB\tCardPr" +
      "oto"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_com_protos_Card_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_com_protos_Card_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessage.FieldAccessorTable(
        internal_static_com_protos_Card_descriptor,
        new java.lang.String[] { "HasBeenUsed", "InHand", "Id", "Name", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
