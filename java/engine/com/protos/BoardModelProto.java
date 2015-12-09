// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: board_model.proto

package com.protos;

public final class BoardModelProto {
  private BoardModelProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  public interface BoardModelOrBuilder extends
      // @@protoc_insertion_point(interface_extends:com.protos.BoardModel)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>required .com.protos.PlayerModel player = 1;</code>
     */
    boolean hasPlayer();
    /**
     * <code>required .com.protos.PlayerModel player = 1;</code>
     */
    com.protos.PlayerModelProto.PlayerModel getPlayer();
    /**
     * <code>required .com.protos.PlayerModel player = 1;</code>
     */
    com.protos.PlayerModelProto.PlayerModelOrBuilder getPlayerOrBuilder();

    /**
     * <code>required .com.protos.PlayerModel opponent = 2;</code>
     */
    boolean hasOpponent();
    /**
     * <code>required .com.protos.PlayerModel opponent = 2;</code>
     */
    com.protos.PlayerModelProto.PlayerModel getOpponent();
    /**
     * <code>required .com.protos.PlayerModel opponent = 2;</code>
     */
    com.protos.PlayerModelProto.PlayerModelOrBuilder getOpponentOrBuilder();
  }
  /**
   * Protobuf type {@code com.protos.BoardModel}
   */
  public static final class BoardModel extends
      com.google.protobuf.GeneratedMessage implements
      // @@protoc_insertion_point(message_implements:com.protos.BoardModel)
      BoardModelOrBuilder {
    // Use BoardModel.newBuilder() to construct.
    private BoardModel(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
      super(builder);
      this.unknownFields = builder.getUnknownFields();
    }
    private BoardModel(boolean noInit) { this.unknownFields = com.google.protobuf.UnknownFieldSet.getDefaultInstance(); }

    private static final BoardModel defaultInstance;
    public static BoardModel getDefaultInstance() {
      return defaultInstance;
    }

    public BoardModel getDefaultInstanceForType() {
      return defaultInstance;
    }

    private final com.google.protobuf.UnknownFieldSet unknownFields;
    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
        getUnknownFields() {
      return this.unknownFields;
    }
    private BoardModel(
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
            case 10: {
              com.protos.PlayerModelProto.PlayerModel.Builder subBuilder = null;
              if (((bitField0_ & 0x00000001) == 0x00000001)) {
                subBuilder = player_.toBuilder();
              }
              player_ = input.readMessage(com.protos.PlayerModelProto.PlayerModel.PARSER, extensionRegistry);
              if (subBuilder != null) {
                subBuilder.mergeFrom(player_);
                player_ = subBuilder.buildPartial();
              }
              bitField0_ |= 0x00000001;
              break;
            }
            case 18: {
              com.protos.PlayerModelProto.PlayerModel.Builder subBuilder = null;
              if (((bitField0_ & 0x00000002) == 0x00000002)) {
                subBuilder = opponent_.toBuilder();
              }
              opponent_ = input.readMessage(com.protos.PlayerModelProto.PlayerModel.PARSER, extensionRegistry);
              if (subBuilder != null) {
                subBuilder.mergeFrom(opponent_);
                opponent_ = subBuilder.buildPartial();
              }
              bitField0_ |= 0x00000002;
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
      return com.protos.BoardModelProto.internal_static_com_protos_BoardModel_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.protos.BoardModelProto.internal_static_com_protos_BoardModel_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.protos.BoardModelProto.BoardModel.class, com.protos.BoardModelProto.BoardModel.Builder.class);
    }

    public static com.google.protobuf.Parser<BoardModel> PARSER =
        new com.google.protobuf.AbstractParser<BoardModel>() {
      public BoardModel parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new BoardModel(input, extensionRegistry);
      }
    };

    @java.lang.Override
    public com.google.protobuf.Parser<BoardModel> getParserForType() {
      return PARSER;
    }

    private int bitField0_;
    public static final int PLAYER_FIELD_NUMBER = 1;
    private com.protos.PlayerModelProto.PlayerModel player_;
    /**
     * <code>required .com.protos.PlayerModel player = 1;</code>
     */
    public boolean hasPlayer() {
      return ((bitField0_ & 0x00000001) == 0x00000001);
    }
    /**
     * <code>required .com.protos.PlayerModel player = 1;</code>
     */
    public com.protos.PlayerModelProto.PlayerModel getPlayer() {
      return player_;
    }
    /**
     * <code>required .com.protos.PlayerModel player = 1;</code>
     */
    public com.protos.PlayerModelProto.PlayerModelOrBuilder getPlayerOrBuilder() {
      return player_;
    }

    public static final int OPPONENT_FIELD_NUMBER = 2;
    private com.protos.PlayerModelProto.PlayerModel opponent_;
    /**
     * <code>required .com.protos.PlayerModel opponent = 2;</code>
     */
    public boolean hasOpponent() {
      return ((bitField0_ & 0x00000002) == 0x00000002);
    }
    /**
     * <code>required .com.protos.PlayerModel opponent = 2;</code>
     */
    public com.protos.PlayerModelProto.PlayerModel getOpponent() {
      return opponent_;
    }
    /**
     * <code>required .com.protos.PlayerModel opponent = 2;</code>
     */
    public com.protos.PlayerModelProto.PlayerModelOrBuilder getOpponentOrBuilder() {
      return opponent_;
    }

    private void initFields() {
      player_ = com.protos.PlayerModelProto.PlayerModel.getDefaultInstance();
      opponent_ = com.protos.PlayerModelProto.PlayerModel.getDefaultInstance();
    }
    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      if (!hasPlayer()) {
        memoizedIsInitialized = 0;
        return false;
      }
      if (!hasOpponent()) {
        memoizedIsInitialized = 0;
        return false;
      }
      if (!getPlayer().isInitialized()) {
        memoizedIsInitialized = 0;
        return false;
      }
      if (!getOpponent().isInitialized()) {
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
        output.writeMessage(1, player_);
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        output.writeMessage(2, opponent_);
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
          .computeMessageSize(1, player_);
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        size += com.google.protobuf.CodedOutputStream
          .computeMessageSize(2, opponent_);
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

    public static com.protos.BoardModelProto.BoardModel parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.protos.BoardModelProto.BoardModel parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.protos.BoardModelProto.BoardModel parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.protos.BoardModelProto.BoardModel parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.protos.BoardModelProto.BoardModel parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static com.protos.BoardModelProto.BoardModel parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }
    public static com.protos.BoardModelProto.BoardModel parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input);
    }
    public static com.protos.BoardModelProto.BoardModel parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input, extensionRegistry);
    }
    public static com.protos.BoardModelProto.BoardModel parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static com.protos.BoardModelProto.BoardModel parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }

    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(com.protos.BoardModelProto.BoardModel prototype) {
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
     * Protobuf type {@code com.protos.BoardModel}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:com.protos.BoardModel)
        com.protos.BoardModelProto.BoardModelOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.protos.BoardModelProto.internal_static_com_protos_BoardModel_descriptor;
      }

      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.protos.BoardModelProto.internal_static_com_protos_BoardModel_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.protos.BoardModelProto.BoardModel.class, com.protos.BoardModelProto.BoardModel.Builder.class);
      }

      // Construct using com.protos.BoardModelProto.BoardModel.newBuilder()
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
          getPlayerFieldBuilder();
          getOpponentFieldBuilder();
        }
      }
      private static Builder create() {
        return new Builder();
      }

      public Builder clear() {
        super.clear();
        if (playerBuilder_ == null) {
          player_ = com.protos.PlayerModelProto.PlayerModel.getDefaultInstance();
        } else {
          playerBuilder_.clear();
        }
        bitField0_ = (bitField0_ & ~0x00000001);
        if (opponentBuilder_ == null) {
          opponent_ = com.protos.PlayerModelProto.PlayerModel.getDefaultInstance();
        } else {
          opponentBuilder_.clear();
        }
        bitField0_ = (bitField0_ & ~0x00000002);
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(buildPartial());
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.protos.BoardModelProto.internal_static_com_protos_BoardModel_descriptor;
      }

      public com.protos.BoardModelProto.BoardModel getDefaultInstanceForType() {
        return com.protos.BoardModelProto.BoardModel.getDefaultInstance();
      }

      public com.protos.BoardModelProto.BoardModel build() {
        com.protos.BoardModelProto.BoardModel result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public com.protos.BoardModelProto.BoardModel buildPartial() {
        com.protos.BoardModelProto.BoardModel result = new com.protos.BoardModelProto.BoardModel(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
          to_bitField0_ |= 0x00000001;
        }
        if (playerBuilder_ == null) {
          result.player_ = player_;
        } else {
          result.player_ = playerBuilder_.build();
        }
        if (((from_bitField0_ & 0x00000002) == 0x00000002)) {
          to_bitField0_ |= 0x00000002;
        }
        if (opponentBuilder_ == null) {
          result.opponent_ = opponent_;
        } else {
          result.opponent_ = opponentBuilder_.build();
        }
        result.bitField0_ = to_bitField0_;
        onBuilt();
        return result;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof com.protos.BoardModelProto.BoardModel) {
          return mergeFrom((com.protos.BoardModelProto.BoardModel)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.protos.BoardModelProto.BoardModel other) {
        if (other == com.protos.BoardModelProto.BoardModel.getDefaultInstance()) return this;
        if (other.hasPlayer()) {
          mergePlayer(other.getPlayer());
        }
        if (other.hasOpponent()) {
          mergeOpponent(other.getOpponent());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public final boolean isInitialized() {
        if (!hasPlayer()) {
          
          return false;
        }
        if (!hasOpponent()) {
          
          return false;
        }
        if (!getPlayer().isInitialized()) {
          
          return false;
        }
        if (!getOpponent().isInitialized()) {
          
          return false;
        }
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        com.protos.BoardModelProto.BoardModel parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (com.protos.BoardModelProto.BoardModel) e.getUnfinishedMessage();
          throw e;
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      private com.protos.PlayerModelProto.PlayerModel player_ = com.protos.PlayerModelProto.PlayerModel.getDefaultInstance();
      private com.google.protobuf.SingleFieldBuilder<
          com.protos.PlayerModelProto.PlayerModel, com.protos.PlayerModelProto.PlayerModel.Builder, com.protos.PlayerModelProto.PlayerModelOrBuilder> playerBuilder_;
      /**
       * <code>required .com.protos.PlayerModel player = 1;</code>
       */
      public boolean hasPlayer() {
        return ((bitField0_ & 0x00000001) == 0x00000001);
      }
      /**
       * <code>required .com.protos.PlayerModel player = 1;</code>
       */
      public com.protos.PlayerModelProto.PlayerModel getPlayer() {
        if (playerBuilder_ == null) {
          return player_;
        } else {
          return playerBuilder_.getMessage();
        }
      }
      /**
       * <code>required .com.protos.PlayerModel player = 1;</code>
       */
      public Builder setPlayer(com.protos.PlayerModelProto.PlayerModel value) {
        if (playerBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          player_ = value;
          onChanged();
        } else {
          playerBuilder_.setMessage(value);
        }
        bitField0_ |= 0x00000001;
        return this;
      }
      /**
       * <code>required .com.protos.PlayerModel player = 1;</code>
       */
      public Builder setPlayer(
          com.protos.PlayerModelProto.PlayerModel.Builder builderForValue) {
        if (playerBuilder_ == null) {
          player_ = builderForValue.build();
          onChanged();
        } else {
          playerBuilder_.setMessage(builderForValue.build());
        }
        bitField0_ |= 0x00000001;
        return this;
      }
      /**
       * <code>required .com.protos.PlayerModel player = 1;</code>
       */
      public Builder mergePlayer(com.protos.PlayerModelProto.PlayerModel value) {
        if (playerBuilder_ == null) {
          if (((bitField0_ & 0x00000001) == 0x00000001) &&
              player_ != com.protos.PlayerModelProto.PlayerModel.getDefaultInstance()) {
            player_ =
              com.protos.PlayerModelProto.PlayerModel.newBuilder(player_).mergeFrom(value).buildPartial();
          } else {
            player_ = value;
          }
          onChanged();
        } else {
          playerBuilder_.mergeFrom(value);
        }
        bitField0_ |= 0x00000001;
        return this;
      }
      /**
       * <code>required .com.protos.PlayerModel player = 1;</code>
       */
      public Builder clearPlayer() {
        if (playerBuilder_ == null) {
          player_ = com.protos.PlayerModelProto.PlayerModel.getDefaultInstance();
          onChanged();
        } else {
          playerBuilder_.clear();
        }
        bitField0_ = (bitField0_ & ~0x00000001);
        return this;
      }
      /**
       * <code>required .com.protos.PlayerModel player = 1;</code>
       */
      public com.protos.PlayerModelProto.PlayerModel.Builder getPlayerBuilder() {
        bitField0_ |= 0x00000001;
        onChanged();
        return getPlayerFieldBuilder().getBuilder();
      }
      /**
       * <code>required .com.protos.PlayerModel player = 1;</code>
       */
      public com.protos.PlayerModelProto.PlayerModelOrBuilder getPlayerOrBuilder() {
        if (playerBuilder_ != null) {
          return playerBuilder_.getMessageOrBuilder();
        } else {
          return player_;
        }
      }
      /**
       * <code>required .com.protos.PlayerModel player = 1;</code>
       */
      private com.google.protobuf.SingleFieldBuilder<
          com.protos.PlayerModelProto.PlayerModel, com.protos.PlayerModelProto.PlayerModel.Builder, com.protos.PlayerModelProto.PlayerModelOrBuilder> 
          getPlayerFieldBuilder() {
        if (playerBuilder_ == null) {
          playerBuilder_ = new com.google.protobuf.SingleFieldBuilder<
              com.protos.PlayerModelProto.PlayerModel, com.protos.PlayerModelProto.PlayerModel.Builder, com.protos.PlayerModelProto.PlayerModelOrBuilder>(
                  getPlayer(),
                  getParentForChildren(),
                  isClean());
          player_ = null;
        }
        return playerBuilder_;
      }

      private com.protos.PlayerModelProto.PlayerModel opponent_ = com.protos.PlayerModelProto.PlayerModel.getDefaultInstance();
      private com.google.protobuf.SingleFieldBuilder<
          com.protos.PlayerModelProto.PlayerModel, com.protos.PlayerModelProto.PlayerModel.Builder, com.protos.PlayerModelProto.PlayerModelOrBuilder> opponentBuilder_;
      /**
       * <code>required .com.protos.PlayerModel opponent = 2;</code>
       */
      public boolean hasOpponent() {
        return ((bitField0_ & 0x00000002) == 0x00000002);
      }
      /**
       * <code>required .com.protos.PlayerModel opponent = 2;</code>
       */
      public com.protos.PlayerModelProto.PlayerModel getOpponent() {
        if (opponentBuilder_ == null) {
          return opponent_;
        } else {
          return opponentBuilder_.getMessage();
        }
      }
      /**
       * <code>required .com.protos.PlayerModel opponent = 2;</code>
       */
      public Builder setOpponent(com.protos.PlayerModelProto.PlayerModel value) {
        if (opponentBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          opponent_ = value;
          onChanged();
        } else {
          opponentBuilder_.setMessage(value);
        }
        bitField0_ |= 0x00000002;
        return this;
      }
      /**
       * <code>required .com.protos.PlayerModel opponent = 2;</code>
       */
      public Builder setOpponent(
          com.protos.PlayerModelProto.PlayerModel.Builder builderForValue) {
        if (opponentBuilder_ == null) {
          opponent_ = builderForValue.build();
          onChanged();
        } else {
          opponentBuilder_.setMessage(builderForValue.build());
        }
        bitField0_ |= 0x00000002;
        return this;
      }
      /**
       * <code>required .com.protos.PlayerModel opponent = 2;</code>
       */
      public Builder mergeOpponent(com.protos.PlayerModelProto.PlayerModel value) {
        if (opponentBuilder_ == null) {
          if (((bitField0_ & 0x00000002) == 0x00000002) &&
              opponent_ != com.protos.PlayerModelProto.PlayerModel.getDefaultInstance()) {
            opponent_ =
              com.protos.PlayerModelProto.PlayerModel.newBuilder(opponent_).mergeFrom(value).buildPartial();
          } else {
            opponent_ = value;
          }
          onChanged();
        } else {
          opponentBuilder_.mergeFrom(value);
        }
        bitField0_ |= 0x00000002;
        return this;
      }
      /**
       * <code>required .com.protos.PlayerModel opponent = 2;</code>
       */
      public Builder clearOpponent() {
        if (opponentBuilder_ == null) {
          opponent_ = com.protos.PlayerModelProto.PlayerModel.getDefaultInstance();
          onChanged();
        } else {
          opponentBuilder_.clear();
        }
        bitField0_ = (bitField0_ & ~0x00000002);
        return this;
      }
      /**
       * <code>required .com.protos.PlayerModel opponent = 2;</code>
       */
      public com.protos.PlayerModelProto.PlayerModel.Builder getOpponentBuilder() {
        bitField0_ |= 0x00000002;
        onChanged();
        return getOpponentFieldBuilder().getBuilder();
      }
      /**
       * <code>required .com.protos.PlayerModel opponent = 2;</code>
       */
      public com.protos.PlayerModelProto.PlayerModelOrBuilder getOpponentOrBuilder() {
        if (opponentBuilder_ != null) {
          return opponentBuilder_.getMessageOrBuilder();
        } else {
          return opponent_;
        }
      }
      /**
       * <code>required .com.protos.PlayerModel opponent = 2;</code>
       */
      private com.google.protobuf.SingleFieldBuilder<
          com.protos.PlayerModelProto.PlayerModel, com.protos.PlayerModelProto.PlayerModel.Builder, com.protos.PlayerModelProto.PlayerModelOrBuilder> 
          getOpponentFieldBuilder() {
        if (opponentBuilder_ == null) {
          opponentBuilder_ = new com.google.protobuf.SingleFieldBuilder<
              com.protos.PlayerModelProto.PlayerModel, com.protos.PlayerModelProto.PlayerModel.Builder, com.protos.PlayerModelProto.PlayerModelOrBuilder>(
                  getOpponent(),
                  getParentForChildren(),
                  isClean());
          opponent_ = null;
        }
        return opponentBuilder_;
      }

      // @@protoc_insertion_point(builder_scope:com.protos.BoardModel)
    }

    static {
      defaultInstance = new BoardModel(true);
      defaultInstance.initFields();
    }

    // @@protoc_insertion_point(class_scope:com.protos.BoardModel)
  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_protos_BoardModel_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_com_protos_BoardModel_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\021board_model.proto\022\ncom.protos\032\022player_" +
      "model.proto\"`\n\nBoardModel\022\'\n\006player\030\001 \002(" +
      "\0132\027.com.protos.PlayerModel\022)\n\010opponent\030\002" +
      " \002(\0132\027.com.protos.PlayerModelB\035\n\ncom.pro" +
      "tosB\017BoardModelProto"
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
          com.protos.PlayerModelProto.getDescriptor(),
        }, assigner);
    internal_static_com_protos_BoardModel_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_com_protos_BoardModel_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessage.FieldAccessorTable(
        internal_static_com_protos_BoardModel_descriptor,
        new java.lang.String[] { "Player", "Opponent", });
    com.protos.PlayerModelProto.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
