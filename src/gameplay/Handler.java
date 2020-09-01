package gameplay;

import java.awt.*;

import java.util.ArrayList;

public class Handler {

    public ArrayList<GameObject> objects;
    public ArrayList<GamePhantomObject>[] phatomObjects; //for every phantom key

    public Handler(){
        objects = new ArrayList<>();
        phatomObjects = new ArrayList[26];

        for(int j = 0; j < phatomObjects.length; j++){
            phatomObjects[j] = new ArrayList<>();
        }
    }

    public void tick(){
        objects.forEach(gameObject -> gameObject.tick());
        for(int i = 0; i < phatomObjects.length; i++)
            phatomObjects[i].forEach(GamePhantomObject::tick);
    }

    public void render(Graphics2D g2d){
        objects.forEach(gameObject -> gameObject.render(g2d));
        for(int i = 0; i < phatomObjects.length; i++) {
            for (int j = 0; j < phatomObjects[i].size(); j++) {
                phatomObjects[i].get(j).render(g2d);
            }
        }
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
