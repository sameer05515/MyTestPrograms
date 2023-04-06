import { configureStore } from "@reduxjs/toolkit";
import prescSclice from "./prescription-slice";

const store=configureStore({
    reducer:{ pres: prescSclice.reducer}
});

export default store;