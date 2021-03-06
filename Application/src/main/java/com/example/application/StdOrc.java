package com.example.application;

        import javafx.geometry.Bounds;
        import javafx.scene.image.Image;
        import javafx.scene.image.ImageView;
        import javafx.scene.layout.AnchorPane;

        import java.io.IOException;
        import java.io.ObjectInputStream;
        import java.io.ObjectOutputStream;
        import java.io.Serial;

public class StdOrc extends GreenOrc{
    @Serial
    private static final long serialVersionUID = 16L;
    public StdOrc(double xloc, double yloc) {
        super(xloc, yloc, 1, 20);
        render();
    }

    private void render(){
        Image image = new Image("orcfinal.png");
        ImageView iv = new ImageView(image);
        iv.setPickOnBounds(true);
        iv.setPreserveRatio(true);
        iv.setX(this.getLocation().getX()); iv.setY(this.getLocation().getY());
        iv.setFitHeight(59); iv.setFitWidth(69);
        this.setImageView(iv);
    }

    @Serial
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
    }
    @Serial
    private void readObject(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        inputStream.defaultReadObject();
        render();
    }

}
