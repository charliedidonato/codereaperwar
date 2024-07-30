
package com.empirestateids.domain;

import java.io.IOException;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.*;
import com.fasterxml.jackson.core.type.*;
import java.util.List;
import java.util.Objects;

@JsonDeserialize(using = AttributeUnion.Deserializer.class)
@JsonSerialize(using = AttributeUnion.Serializer.class)
public class AttributeUnion {

	public List<AttributeElement> attributeElementArrayValue;
    public AttributeElement attributeElementValue;

    static class Deserializer extends JsonDeserializer<AttributeUnion> {
        @Override
        public AttributeUnion deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            AttributeUnion value = new AttributeUnion();
            switch (jsonParser.currentToken()) {
                case START_ARRAY:
                    value.attributeElementArrayValue = jsonParser.readValueAs(new TypeReference<List>() {});
                    break;
                case START_OBJECT:
                    value.attributeElementValue = jsonParser.readValueAs(AttributeElement.class);
                    break;
                default: throw new IOException("Cannot deserialize AttributeUnion");
            }
            return value;
        }
    }

    static class Serializer extends JsonSerializer<AttributeUnion> {
        @Override
        public void serialize(AttributeUnion obj, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            if (obj.attributeElementArrayValue != null) {
                jsonGenerator.writeObject(obj.attributeElementArrayValue);
                return;
            }
            if (obj.attributeElementValue != null) {
                jsonGenerator.writeObject(obj.attributeElementValue);
                return;
            }
            throw new IOException("AttributeUnion must not be null");
        }
    }

    @Override
	public String toString() {
		return "AttributeUnion [attributeElementArrayValue=" + attributeElementArrayValue + ", attributeElementValue="
				+ attributeElementValue + "]";
	}


	@Override
	public int hashCode() {
		return Objects.hash(attributeElementArrayValue, attributeElementValue);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AttributeUnion other = (AttributeUnion) obj;
		return Objects.equals(attributeElementArrayValue, other.attributeElementArrayValue)
				&& Objects.equals(attributeElementValue, other.attributeElementValue);
	}
}