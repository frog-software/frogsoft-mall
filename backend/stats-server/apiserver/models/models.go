package models

type GenderResponse struct {
	Female  int `json:"female"`
	Male    int `json:"male"`
	Unknown int `json:"unknown"`
}

type AgeResponse struct {
	Gender   int `json:"gender"`
	AgeRange int `json:"ageRange"`
	Count    int `json:"count"`
}

type ProvinceResponse struct {
	Province string `json:"province"`
	Count    int    `json:"count"`
}

type ActionResponse struct {
	Click     int `json:"click"`
	Add       int `json:"add"`
	Purchase  int `json:"purchase"`
	Subscribe int `json:"subscribe"`
}

type CategoryResponse struct {
	Category int `json:"category"`
	Count    int `json:"count"`
}

type PredictionResponse struct {
	Prediction bool `json:"prediction"`
}
