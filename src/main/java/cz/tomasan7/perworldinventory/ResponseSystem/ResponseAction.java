package cz.tomasan7.perworldinventory.ResponseSystem;

@FunctionalInterface
public interface ResponseAction
{
    String perform (String response);
}
