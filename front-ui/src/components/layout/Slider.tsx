import React from 'react';
import {
  ArrowLeft, ArrowRight, ChevronLeft, ChevronRight,
} from '@mui/icons-material';
import IconButton from '@mui/material/IconButton';

type SliderProps = {
  children: React.ReactNode,
  previous: () => void,
  next: () => void,
  style?: 'chevron' | 'arrow',
};
export default function Slider({
  children, previous, next, style = 'arrow',
}: SliderProps) {
  return (
    <div className="slider">
      <div className="slider-content">
        <IconButton
          onClick={previous}
        >
          {style === 'arrow' && <ArrowLeft/>}
          {style === 'chevron' && <ChevronLeft/>}
        </IconButton>
          {children}
        <IconButton
          onClick={next}
        >
          {style === 'arrow' && <ArrowRight/>}
          {style === 'chevron' && <ChevronRight/>}
        </IconButton>
      </div>
    </div>
  );
}
