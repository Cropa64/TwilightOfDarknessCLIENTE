package utilidades;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

public class Entrada implements InputProcessor {

    public boolean pressedEnter;
    public boolean pressedW;
    public boolean pressedA;
    public boolean pressedS;
    public boolean pressedD;
    public boolean pressedMas;
    public boolean pressedMenos;
    public boolean pressedShift;
    public boolean pressedEsc;
    public boolean pressedClickIzq, pressedClickDer;
    public boolean zoom;
    public boolean pressed1, pressed2, pressed3, pressed4, pressed5;

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {

            case Input.Keys.ENTER:
                pressedEnter = true;
                return true;
            case Input.Keys.W:
                pressedW = true;
                return true;
            case Input.Keys.A:
                pressedA = true;
                return true;
            case Input.Keys.S:
                pressedS = true;
                return true;
            case Input.Keys.D:
                pressedD = true;
                return true;
            case Input.Keys.PAGE_UP:
                pressedMas = true;
                return true;
            case Input.Keys.PAGE_DOWN:
                pressedMenos = true;
                return true;
            case Input.Keys.SHIFT_LEFT:
                pressedShift = true;
                return true;
            case Input.Keys.ESCAPE:
                pressedEsc = true;
                return true;
            case Input.Keys.NUM_1:
                pressed1 = true;
                return true;
            case Input.Keys.NUM_2:
                pressed2 = true;
                return true;
            case Input.Keys.NUM_3:
                pressed3 = true;
                return true;
            case Input.Keys.NUM_4:
                pressed4 = true;
                return true;
            case Input.Keys.NUM_5:
                pressed5 = true;
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {

            case Input.Keys.ENTER:
                pressedEnter = false;
                return true;
            case Input.Keys.W:
                pressedW = false;
                return true;
            case Input.Keys.A:
                pressedA = false;
                return true;
            case Input.Keys.S:
                pressedS = false;
                return true;
            case Input.Keys.D:
                pressedD = false;
                return true;
            case Input.Keys.PAGE_UP:
                pressedMas = false;
                return true;
            case Input.Keys.PAGE_DOWN:
                pressedMenos = false;
                return true;
            case Input.Keys.SHIFT_LEFT:
                pressedShift = false;
                return true;
            case Input.Keys.ESCAPE:
                pressedEsc = false;
                return true;
            case Input.Keys.NUM_1:
                pressed1 = false;
                return true;
            case Input.Keys.NUM_2:
                pressed2 = false;
                return true;
            case Input.Keys.NUM_3:
                pressed3 = false;
                return true;
            case Input.Keys.NUM_4:
                pressed4 = false;
                return true;
            case Input.Keys.NUM_5:
                pressed5 = false;
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean keyTyped(char character) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        switch (button) {

            case Input.Buttons.LEFT:
                pressedClickIzq = true;
                return true;
            case Input.Buttons.RIGHT:
                pressedClickDer = true;
                return true;
            default:
                return false;

        }
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        switch (button) {

            case Input.Buttons.LEFT:
                pressedClickIzq = false;
                return true;
            case Input.Buttons.RIGHT:
                pressedClickDer = false;
                return true;
            default:
                return false;

        }
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean scrolled(int amount) {

        switch (amount) {

            case 1:
                zoom = true;
                return true;
            case -1:
                zoom = false;
                return true;
            default:
                zoom = false;
                return true;

        }

    }

}
