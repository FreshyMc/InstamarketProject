package com.example.instamarket.model.view;

public class OptionViewModel {
    private Long id;
    private String optionName;
    private String optionValue;

    public OptionViewModel() {
    }

    public Long getId() {
        return id;
    }

    public OptionViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getOptionName() {
        return optionName;
    }

    public OptionViewModel setOptionName(String optionName) {
        this.optionName = optionName;
        return this;
    }

    public String getOptionValue() {
        return optionValue;
    }

    public OptionViewModel setOptionValue(String optionValue) {
        this.optionValue = optionValue;
        return this;
    }
}
