package gameplay;

import java.awt.*;
import java.util.LinkedList;

public class Handler {

    public LinkedList<GameObject> objects;
    public LinkedList<GamePhantomObject>[] phatomObjects; //for every phantom key

    public Handler(){
        objects = new LinkedList<>();
        phatomObjects = new LinkedList[26];

        for(int j = 0; j < phatomObjects.length; j++){
            phatomObjects[j] = new LinkedList<>();
        }
    }

    public void tick(){
        objects.forEach(gameObject -> gameObject.tick());
        for(int i = 0; i < phatomObjects.length; i++)
        phatomObjects[i].forEach(phatomObjects -> phatomObjects.tick());
    }

    public void render(Graphics2D g2d){
        objects.forEach(gameObject -> gameObject.render(g2d));
        for(int i = 0; i < phatomObjects.length; i++)
        phatomObjects[i].forEach(phatomObjects -> phatomObjects.render(g2d));
    }

    public void addObject(GameObject object){
        objects.add(object);
    }

    public void addPhantomObject(GamePhantomObject object, int j){
        phatomObjects[j].add(object);
    }

    public void removeObject(GameObject object){
        objects.remove(object);
    }

    public void removePhantomObject(GamePhantomObject object, int j){
        phatomObjects[j].remove(object);
    }

}
