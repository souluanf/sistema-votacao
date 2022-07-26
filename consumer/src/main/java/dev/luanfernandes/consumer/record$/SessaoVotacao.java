/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package dev.luanfernandes.consumer.record$;

import org.apache.avro.generic.GenericArray;
import org.apache.avro.specific.SpecificData;
import org.apache.avro.util.Utf8;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@org.apache.avro.specific.AvroGenerated
public class SessaoVotacao extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = -583798448463541114L;


  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"SessaoVotacao\",\"namespace\":\"dev.luanfernandes.consumer.record\",\"fields\":[{\"name\":\"pauta\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"sessao\",\"type\":\"long\"},{\"name\":\"horaInicio\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"horaFim\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"votoSim\",\"type\":\"int\"},{\"name\":\"votoNao\",\"type\":\"int\"},{\"name\":\"aberta\",\"type\":\"boolean\"}],\"version\":\"1\"}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static final SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<SessaoVotacao> ENCODER =
      new BinaryMessageEncoder<SessaoVotacao>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<SessaoVotacao> DECODER =
      new BinaryMessageDecoder<SessaoVotacao>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageEncoder instance used by this class.
   * @return the message encoder used by this class
   */
  public static BinaryMessageEncoder<SessaoVotacao> getEncoder() {
    return ENCODER;
  }

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   * @return the message decoder used by this class
   */
  public static BinaryMessageDecoder<SessaoVotacao> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
   */
  public static BinaryMessageDecoder<SessaoVotacao> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<SessaoVotacao>(MODEL$, SCHEMA$, resolver);
  }

  /**
   * Serializes this SessaoVotacao to a ByteBuffer.
   * @return a buffer holding the serialized data for this instance
   * @throws java.io.IOException if this instance could not be serialized
   */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /**
   * Deserializes a SessaoVotacao from a ByteBuffer.
   * @param b a byte buffer holding serialized data for an instance of this class
   * @return a SessaoVotacao instance decoded from the given buffer
   * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
   */
  public static SessaoVotacao fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  private java.lang.String pauta;
  private long sessao;
  private java.lang.String horaInicio;
  private java.lang.String horaFim;
  private int votoSim;
  private int votoNao;
  private boolean aberta;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public SessaoVotacao() {}

  /**
   * All-args constructor.
   * @param pauta The new value for pauta
   * @param sessao The new value for sessao
   * @param horaInicio The new value for horaInicio
   * @param horaFim The new value for horaFim
   * @param votoSim The new value for votoSim
   * @param votoNao The new value for votoNao
   * @param aberta The new value for aberta
   */
  public SessaoVotacao(java.lang.String pauta, java.lang.Long sessao, java.lang.String horaInicio, java.lang.String horaFim, java.lang.Integer votoSim, java.lang.Integer votoNao, java.lang.Boolean aberta) {
    this.pauta = pauta;
    this.sessao = sessao;
    this.horaInicio = horaInicio;
    this.horaFim = horaFim;
    this.votoSim = votoSim;
    this.votoNao = votoNao;
    this.aberta = aberta;
  }

  public org.apache.avro.specific.SpecificData getSpecificData() { return MODEL$; }
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return pauta;
    case 1: return sessao;
    case 2: return horaInicio;
    case 3: return horaFim;
    case 4: return votoSim;
    case 5: return votoNao;
    case 6: return aberta;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: pauta = value$ != null ? value$.toString() : null; break;
    case 1: sessao = (java.lang.Long)value$; break;
    case 2: horaInicio = value$ != null ? value$.toString() : null; break;
    case 3: horaFim = value$ != null ? value$.toString() : null; break;
    case 4: votoSim = (java.lang.Integer)value$; break;
    case 5: votoNao = (java.lang.Integer)value$; break;
    case 6: aberta = (java.lang.Boolean)value$; break;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  /**
   * Gets the value of the 'pauta' field.
   * @return The value of the 'pauta' field.
   */
  public java.lang.String getPauta() {
    return pauta;
  }



  /**
   * Gets the value of the 'sessao' field.
   * @return The value of the 'sessao' field.
   */
  public long getSessao() {
    return sessao;
  }



  /**
   * Gets the value of the 'horaInicio' field.
   * @return The value of the 'horaInicio' field.
   */
  public java.lang.String getHoraInicio() {
    return horaInicio;
  }



  /**
   * Gets the value of the 'horaFim' field.
   * @return The value of the 'horaFim' field.
   */
  public java.lang.String getHoraFim() {
    return horaFim;
  }



  /**
   * Gets the value of the 'votoSim' field.
   * @return The value of the 'votoSim' field.
   */
  public int getVotoSim() {
    return votoSim;
  }



  /**
   * Gets the value of the 'votoNao' field.
   * @return The value of the 'votoNao' field.
   */
  public int getVotoNao() {
    return votoNao;
  }



  /**
   * Gets the value of the 'aberta' field.
   * @return The value of the 'aberta' field.
   */
  public boolean getAberta() {
    return aberta;
  }



  /**
   * Creates a new SessaoVotacao RecordBuilder.
   * @return A new SessaoVotacao RecordBuilder
   */
  public static dev.luanfernandes.consumer.record$.SessaoVotacao.Builder newBuilder() {
    return new dev.luanfernandes.consumer.record$.SessaoVotacao.Builder();
  }

  /**
   * Creates a new SessaoVotacao RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new SessaoVotacao RecordBuilder
   */
  public static dev.luanfernandes.consumer.record$.SessaoVotacao.Builder newBuilder(dev.luanfernandes.consumer.record$.SessaoVotacao.Builder other) {
    if (other == null) {
      return new dev.luanfernandes.consumer.record$.SessaoVotacao.Builder();
    } else {
      return new dev.luanfernandes.consumer.record$.SessaoVotacao.Builder(other);
    }
  }

  /**
   * Creates a new SessaoVotacao RecordBuilder by copying an existing SessaoVotacao instance.
   * @param other The existing instance to copy.
   * @return A new SessaoVotacao RecordBuilder
   */
  public static dev.luanfernandes.consumer.record$.SessaoVotacao.Builder newBuilder(dev.luanfernandes.consumer.record$.SessaoVotacao other) {
    if (other == null) {
      return new dev.luanfernandes.consumer.record$.SessaoVotacao.Builder();
    } else {
      return new dev.luanfernandes.consumer.record$.SessaoVotacao.Builder(other);
    }
  }

  /**
   * RecordBuilder for SessaoVotacao instances.
   */
  @org.apache.avro.specific.AvroGenerated
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<SessaoVotacao>
    implements org.apache.avro.data.RecordBuilder<SessaoVotacao> {

    private java.lang.String pauta;
    private long sessao;
    private java.lang.String horaInicio;
    private java.lang.String horaFim;
    private int votoSim;
    private int votoNao;
    private boolean aberta;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$, MODEL$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(dev.luanfernandes.consumer.record$.SessaoVotacao.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.pauta)) {
        this.pauta = data().deepCopy(fields()[0].schema(), other.pauta);
        fieldSetFlags()[0] = other.fieldSetFlags()[0];
      }
      if (isValidValue(fields()[1], other.sessao)) {
        this.sessao = data().deepCopy(fields()[1].schema(), other.sessao);
        fieldSetFlags()[1] = other.fieldSetFlags()[1];
      }
      if (isValidValue(fields()[2], other.horaInicio)) {
        this.horaInicio = data().deepCopy(fields()[2].schema(), other.horaInicio);
        fieldSetFlags()[2] = other.fieldSetFlags()[2];
      }
      if (isValidValue(fields()[3], other.horaFim)) {
        this.horaFim = data().deepCopy(fields()[3].schema(), other.horaFim);
        fieldSetFlags()[3] = other.fieldSetFlags()[3];
      }
      if (isValidValue(fields()[4], other.votoSim)) {
        this.votoSim = data().deepCopy(fields()[4].schema(), other.votoSim);
        fieldSetFlags()[4] = other.fieldSetFlags()[4];
      }
      if (isValidValue(fields()[5], other.votoNao)) {
        this.votoNao = data().deepCopy(fields()[5].schema(), other.votoNao);
        fieldSetFlags()[5] = other.fieldSetFlags()[5];
      }
      if (isValidValue(fields()[6], other.aberta)) {
        this.aberta = data().deepCopy(fields()[6].schema(), other.aberta);
        fieldSetFlags()[6] = other.fieldSetFlags()[6];
      }
    }

    /**
     * Creates a Builder by copying an existing SessaoVotacao instance
     * @param other The existing instance to copy.
     */
    private Builder(dev.luanfernandes.consumer.record$.SessaoVotacao other) {
      super(SCHEMA$, MODEL$);
      if (isValidValue(fields()[0], other.pauta)) {
        this.pauta = data().deepCopy(fields()[0].schema(), other.pauta);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.sessao)) {
        this.sessao = data().deepCopy(fields()[1].schema(), other.sessao);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.horaInicio)) {
        this.horaInicio = data().deepCopy(fields()[2].schema(), other.horaInicio);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.horaFim)) {
        this.horaFim = data().deepCopy(fields()[3].schema(), other.horaFim);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.votoSim)) {
        this.votoSim = data().deepCopy(fields()[4].schema(), other.votoSim);
        fieldSetFlags()[4] = true;
      }
      if (isValidValue(fields()[5], other.votoNao)) {
        this.votoNao = data().deepCopy(fields()[5].schema(), other.votoNao);
        fieldSetFlags()[5] = true;
      }
      if (isValidValue(fields()[6], other.aberta)) {
        this.aberta = data().deepCopy(fields()[6].schema(), other.aberta);
        fieldSetFlags()[6] = true;
      }
    }

    /**
      * Gets the value of the 'pauta' field.
      * @return The value.
      */
    public java.lang.String getPauta() {
      return pauta;
    }


    /**
      * Sets the value of the 'pauta' field.
      * @param value The value of 'pauta'.
      * @return This builder.
      */
    public dev.luanfernandes.consumer.record$.SessaoVotacao.Builder setPauta(java.lang.String value) {
      validate(fields()[0], value);
      this.pauta = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'pauta' field has been set.
      * @return True if the 'pauta' field has been set, false otherwise.
      */
    public boolean hasPauta() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'pauta' field.
      * @return This builder.
      */
    public dev.luanfernandes.consumer.record$.SessaoVotacao.Builder clearPauta() {
      pauta = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'sessao' field.
      * @return The value.
      */
    public long getSessao() {
      return sessao;
    }


    /**
      * Sets the value of the 'sessao' field.
      * @param value The value of 'sessao'.
      * @return This builder.
      */
    public dev.luanfernandes.consumer.record$.SessaoVotacao.Builder setSessao(long value) {
      validate(fields()[1], value);
      this.sessao = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'sessao' field has been set.
      * @return True if the 'sessao' field has been set, false otherwise.
      */
    public boolean hasSessao() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'sessao' field.
      * @return This builder.
      */
    public dev.luanfernandes.consumer.record$.SessaoVotacao.Builder clearSessao() {
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'horaInicio' field.
      * @return The value.
      */
    public java.lang.String getHoraInicio() {
      return horaInicio;
    }


    /**
      * Sets the value of the 'horaInicio' field.
      * @param value The value of 'horaInicio'.
      * @return This builder.
      */
    public dev.luanfernandes.consumer.record$.SessaoVotacao.Builder setHoraInicio(java.lang.String value) {
      validate(fields()[2], value);
      this.horaInicio = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'horaInicio' field has been set.
      * @return True if the 'horaInicio' field has been set, false otherwise.
      */
    public boolean hasHoraInicio() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'horaInicio' field.
      * @return This builder.
      */
    public dev.luanfernandes.consumer.record$.SessaoVotacao.Builder clearHoraInicio() {
      horaInicio = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    /**
      * Gets the value of the 'horaFim' field.
      * @return The value.
      */
    public java.lang.String getHoraFim() {
      return horaFim;
    }


    /**
      * Sets the value of the 'horaFim' field.
      * @param value The value of 'horaFim'.
      * @return This builder.
      */
    public dev.luanfernandes.consumer.record$.SessaoVotacao.Builder setHoraFim(java.lang.String value) {
      validate(fields()[3], value);
      this.horaFim = value;
      fieldSetFlags()[3] = true;
      return this;
    }

    /**
      * Checks whether the 'horaFim' field has been set.
      * @return True if the 'horaFim' field has been set, false otherwise.
      */
    public boolean hasHoraFim() {
      return fieldSetFlags()[3];
    }


    /**
      * Clears the value of the 'horaFim' field.
      * @return This builder.
      */
    public dev.luanfernandes.consumer.record$.SessaoVotacao.Builder clearHoraFim() {
      horaFim = null;
      fieldSetFlags()[3] = false;
      return this;
    }

    /**
      * Gets the value of the 'votoSim' field.
      * @return The value.
      */
    public int getVotoSim() {
      return votoSim;
    }


    /**
      * Sets the value of the 'votoSim' field.
      * @param value The value of 'votoSim'.
      * @return This builder.
      */
    public dev.luanfernandes.consumer.record$.SessaoVotacao.Builder setVotoSim(int value) {
      validate(fields()[4], value);
      this.votoSim = value;
      fieldSetFlags()[4] = true;
      return this;
    }

    /**
      * Checks whether the 'votoSim' field has been set.
      * @return True if the 'votoSim' field has been set, false otherwise.
      */
    public boolean hasVotoSim() {
      return fieldSetFlags()[4];
    }


    /**
      * Clears the value of the 'votoSim' field.
      * @return This builder.
      */
    public dev.luanfernandes.consumer.record$.SessaoVotacao.Builder clearVotoSim() {
      fieldSetFlags()[4] = false;
      return this;
    }

    /**
      * Gets the value of the 'votoNao' field.
      * @return The value.
      */
    public int getVotoNao() {
      return votoNao;
    }


    /**
      * Sets the value of the 'votoNao' field.
      * @param value The value of 'votoNao'.
      * @return This builder.
      */
    public dev.luanfernandes.consumer.record$.SessaoVotacao.Builder setVotoNao(int value) {
      validate(fields()[5], value);
      this.votoNao = value;
      fieldSetFlags()[5] = true;
      return this;
    }

    /**
      * Checks whether the 'votoNao' field has been set.
      * @return True if the 'votoNao' field has been set, false otherwise.
      */
    public boolean hasVotoNao() {
      return fieldSetFlags()[5];
    }


    /**
      * Clears the value of the 'votoNao' field.
      * @return This builder.
      */
    public dev.luanfernandes.consumer.record$.SessaoVotacao.Builder clearVotoNao() {
      fieldSetFlags()[5] = false;
      return this;
    }

    /**
      * Gets the value of the 'aberta' field.
      * @return The value.
      */
    public boolean getAberta() {
      return aberta;
    }


    /**
      * Sets the value of the 'aberta' field.
      * @param value The value of 'aberta'.
      * @return This builder.
      */
    public dev.luanfernandes.consumer.record$.SessaoVotacao.Builder setAberta(boolean value) {
      validate(fields()[6], value);
      this.aberta = value;
      fieldSetFlags()[6] = true;
      return this;
    }

    /**
      * Checks whether the 'aberta' field has been set.
      * @return True if the 'aberta' field has been set, false otherwise.
      */
    public boolean hasAberta() {
      return fieldSetFlags()[6];
    }


    /**
      * Clears the value of the 'aberta' field.
      * @return This builder.
      */
    public dev.luanfernandes.consumer.record$.SessaoVotacao.Builder clearAberta() {
      fieldSetFlags()[6] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public SessaoVotacao build() {
      try {
        SessaoVotacao record = new SessaoVotacao();
        record.pauta = fieldSetFlags()[0] ? this.pauta : (java.lang.String) defaultValue(fields()[0]);
        record.sessao = fieldSetFlags()[1] ? this.sessao : (java.lang.Long) defaultValue(fields()[1]);
        record.horaInicio = fieldSetFlags()[2] ? this.horaInicio : (java.lang.String) defaultValue(fields()[2]);
        record.horaFim = fieldSetFlags()[3] ? this.horaFim : (java.lang.String) defaultValue(fields()[3]);
        record.votoSim = fieldSetFlags()[4] ? this.votoSim : (java.lang.Integer) defaultValue(fields()[4]);
        record.votoNao = fieldSetFlags()[5] ? this.votoNao : (java.lang.Integer) defaultValue(fields()[5]);
        record.aberta = fieldSetFlags()[6] ? this.aberta : (java.lang.Boolean) defaultValue(fields()[6]);
        return record;
      } catch (org.apache.avro.AvroMissingFieldException e) {
        throw e;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<SessaoVotacao>
    WRITER$ = (org.apache.avro.io.DatumWriter<SessaoVotacao>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<SessaoVotacao>
    READER$ = (org.apache.avro.io.DatumReader<SessaoVotacao>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

  @Override protected boolean hasCustomCoders() { return true; }

  @Override public void customEncode(org.apache.avro.io.Encoder out)
    throws java.io.IOException
  {
    out.writeString(this.pauta);

    out.writeLong(this.sessao);

    out.writeString(this.horaInicio);

    out.writeString(this.horaFim);

    out.writeInt(this.votoSim);

    out.writeInt(this.votoNao);

    out.writeBoolean(this.aberta);

  }

  @Override public void customDecode(org.apache.avro.io.ResolvingDecoder in)
    throws java.io.IOException
  {
    org.apache.avro.Schema.Field[] fieldOrder = in.readFieldOrderIfDiff();
    if (fieldOrder == null) {
      this.pauta = in.readString();

      this.sessao = in.readLong();

      this.horaInicio = in.readString();

      this.horaFim = in.readString();

      this.votoSim = in.readInt();

      this.votoNao = in.readInt();

      this.aberta = in.readBoolean();

    } else {
      for (int i = 0; i < 7; i++) {
        switch (fieldOrder[i].pos()) {
        case 0:
          this.pauta = in.readString();
          break;

        case 1:
          this.sessao = in.readLong();
          break;

        case 2:
          this.horaInicio = in.readString();
          break;

        case 3:
          this.horaFim = in.readString();
          break;

        case 4:
          this.votoSim = in.readInt();
          break;

        case 5:
          this.votoNao = in.readInt();
          break;

        case 6:
          this.aberta = in.readBoolean();
          break;

        default:
          throw new java.io.IOException("Corrupt ResolvingDecoder.");
        }
      }
    }
  }
}










