package org.example.view;


import org.example.dto.Model;

public interface CustomView {
    Model begin(Model model);

    String getUri();
}
