import { useState } from 'react';

export type SwipeableIndex = {
  index: number,
  setIndex: React.Dispatch<React.SetStateAction<number>>,
  onPrevious: () => void,
  onNext: () => void,
};
const useSwipeableIndex = () : SwipeableIndex => {
  const [index, setIndex] = useState<number>(0);

  const onPrevious = () => {
    setIndex(index - 1);
  };

  const onNext = () => {
    setIndex(index + 1);
  };

  return {
    index,
    setIndex,
    onPrevious,
    onNext,
  };
};

export default useSwipeableIndex;
