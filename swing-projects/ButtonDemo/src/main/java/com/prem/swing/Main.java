package com.prem.swing;

import com.prem.swing.component.Launchable;
import com.prem.swing.factory.LaunchableFactory;

import static com.prem.swing.factory.LaunchableFactory.Type.ButtonDemo;

public class Main {
    public static void main(String[] args) {

        System.out.println("Button demo");
        Launchable l= LaunchableFactory.getComponent(ButtonDemo);
        l.launch(new String[]{""});
    }
}