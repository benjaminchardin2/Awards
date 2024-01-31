import StarIcon from '@mui/icons-material/Star';
import React from 'react';

export default function GradientIcon() {
  return (
    <>
      <svg width={0} height={0}>
        <linearGradient id="linearColors" x1={0} y1={0} x2={1} y2={1}>
          <stop offset={0} stopColor="#ffbe3d"/>
          <stop offset={1} stopColor="#f06543"/>
        </linearGradient>
      </svg>
      <StarIcon sx={{ fill: 'url(#linearColors)' }}/>
    </>
  );
}
