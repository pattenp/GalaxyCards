package cardCreator;

public class Start {

	
	public static void main(String[] args) {
		CreateController controller = new CreateController();
		controller.setGui(new CreateGui(controller));
	}
}
