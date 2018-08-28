interface Measurement {
    name: string;
    createdDate: string;
}

export interface AftBmt extends Measurement {
    temperatureOne: number;
    temperatureTwo: number;
    temperatureThree: number;
    temperatureFour: number;
}

export interface ForwardBmt extends Measurement {
    temperatureOne: number;
    temperatureTwo: number;
    temperatureThree: number;
    temperatureFour: number;
}

export interface TurbineVibrations extends Measurement {
    vibrationOne: number;
    vibrationTwo: number;
    vibrationThree: number;
    vibrationFour: number;
}

export interface GeneratorVibrations extends Measurement {
    vibrationOne: number;
    vibrationTwo: number;
    vibrationThree: number;
    vibrationFour: number;
}

export interface CompressorEfficiency extends Measurement {
    compressorEfficiecy: number;
}

export interface TurbineEfficiency extends Measurement {
    turbineEfficiecy: number;
}