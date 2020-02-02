package coffee.machine.configurations;

import coffee.machine.CoffeeKind;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CoffeeKindConverter implements Converter<String, CoffeeKind> {
    @Override
    public CoffeeKind convert(String source) {
        try {
            return CoffeeKind.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("Unsupported kind of coffee: %s", source));
        }
    }
}
