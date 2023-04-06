package com.prem.swing.factory;

import com.prem.swing.button.demo.ButtonDemo;
import com.prem.swing.component.Launchable;

public class LaunchableFactory {

    public enum Type{
        ButtonDemo;
    }

    public static Launchable getComponent(Type type){
        switch (type){
            case ButtonDemo:
                return new ButtonDemo();
            default:return null;
        }
    }
}
