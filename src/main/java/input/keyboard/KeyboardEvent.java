package input.keyboard;

@FunctionalInterface
public interface KeyboardEvent
{

	public void onEvent(KeyAction action, int keyID);
}
