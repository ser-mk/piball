package ser.pipi.piball.settings.UI;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;

import java.lang.reflect.Field;

import ser.pipi.piball.Settings;
import ser.pipi.piball.settings.NormalizeNumberField;

/**
 * Created by ser on 19.04.18.
 */

class PrefTable extends Table {

    final Skin skin;

    public PrefTable(Skin skin) {
        this.skin = skin;
    }

    final float scaleLabelText = 0.35f;


    public void addRowPrefText(String label, String firstValue){
        Label labelUI = new Label(label, skin);
        labelUI.setAlignment(Align.right,Align.right);
        labelUI.setFontScale(scaleLabelText);
        this.add(labelUI);
        TextField textField = new TextField(firstValue,skin);
        textField.setName(label);
        this.add(textField);
        if(NormalizeNumberField.mapInterval.containsKey(label)){
            Label normLabel = new Label(NormalizeNumberField.getNote(label), skin);
            normLabel.setAlignment(Align.left);
            normLabel.setFontScale(scaleLabelText);
            this.add(normLabel);
        }
        this.row();
    }

    public void addRowPrefCheckBox(String label, boolean value){
        Label checkLabel = new Label(label, skin);
        checkLabel.setFontScale(scaleLabelText);
        this.add(checkLabel);
        CheckBox checkBox = new CheckBox("(click here)",skin);
        checkBox.setName(label);
        checkBox.setChecked(value);
        this.add(checkBox);
        this.row();
    }

    private <T> boolean checkAndAddClassField(Field field, Settings settings,Class<T> clazz){
        if(!clazz.equals(field.getType())){
            return false;
        }

        try {
            final T obj =  (T)field.get(settings);
            String value = String.valueOf(obj);
            String label = field.getName();
            if(clazz.equals(boolean.class)){
                addRowPrefCheckBox(label,(Boolean)obj);
            } else {
                addRowPrefText(label,value);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean convertSettingsStruct2Table(Settings settings){
        try{
            for (Field field : settings.getClass().getDeclaredFields()) {
                checkAndAddClassField(field, settings,int.class);
                checkAndAddClassField(field, settings,float.class);
                checkAndAddClassField(field, settings,boolean.class);
            }
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    float time_wait = 0;

    @Override
    public void act(float delta) {
        super.act(delta);
        if(time_wait < 5f){
            time_wait += delta;
            return;
        }

        time_wait = 0;
        checkAndCorrectValueTable();

    }

    public static  <T> T getFieldFromDefaultSettings(String name) throws IllegalAccessException, NoSuchFieldException {
        Settings settings = new Settings();

        Field field = settings.getClass().getField(name);
        return (T)field.get(settings);
    }

    private static Integer parseInt(String name, String text){
        int temp = 1;
        try {
            temp = Integer.parseInt(text);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            try {
                temp = getFieldFromDefaultSettings(name);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return temp;
    }

    private static Float parseFloat(String name, String text){
        float temp = 1f;
        try {
            temp = Float.parseFloat(text);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            try {
                temp = getFieldFromDefaultSettings(name);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

        return temp;
    }

    private <T> T getField(String name, Class<T> clazz){
        Actor actor = this.findActor(name);
        if(isNumber(clazz)){
            String text = ((TextField)actor).getText();
            if(clazz.equals(int.class)){
                return (T)parseInt(name,text);
            }
            if(clazz.equals(float.class)){
                return (T)parseFloat(name,text);
            }
        } else if (clazz.equals(boolean.class)){
            Boolean check = ((CheckBox)actor).isChecked();
            return (T)check;
        }

        return null;
    }

    private boolean isNumber(Class clazz){
        if (clazz.equals(int.class))
            return true;
        if (clazz.equals(float.class))
            return true;
        return false;
    }

    public void checkAndCorrectValueTable(){
        try{
            for (Field field : Settings.class.getDeclaredFields()) {
                final String name = field.getName();
                Class clazz = field.getType();
                Object temp = getField(name, clazz);
                if (!NormalizeNumberField.mapInterval.containsKey(name)){
                    continue;
                }
                String text = "0";
                if(clazz.equals(int.class)){
                    Integer tempInt = getField(name, int.class);
                    if(tempInt == null)
                        continue;
                    tempInt = NormalizeNumberField.checkIntInterval(name,tempInt);
                    text = String.valueOf(tempInt);
                } else if (clazz.equals(float.class)){
                    Float tempFloat = getField(name, float.class);
                    if(tempFloat == null)
                        continue;
                    tempFloat = NormalizeNumberField.checkIntInterval(name,tempFloat);
                    text = String.valueOf(tempFloat);
                } else {
                    continue;
                }

                ((TextField)this.findActor(name)).setText(text);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public Settings getSettingsFromTable(){
        final Settings settings = new Settings();
        for (Field field : Settings.class.getDeclaredFields()) {
            final String name = field.getName();
            Class clazz = field.getType();
            Object temp = getField(name, clazz);
            try {
                field.set(settings,temp);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return settings;
    }


}
