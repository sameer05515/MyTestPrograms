import {createSlice} from '@reduxjs/toolkit';

const prescSclice=createSlice({
    name: 'prescription',
    initialState: {items:[]},
    reducers:{
        addItem(state, action){
            const item= action.payload;
            state.items.push(item);
            console.log(state.items)
        },
        editItem(state, action){
            const item= action.payload;
        },
        deleteItem(state, action){
            const item= action.payload;
        }
    }
});

export const prescActions= prescSclice.actions;

export default prescSclice;