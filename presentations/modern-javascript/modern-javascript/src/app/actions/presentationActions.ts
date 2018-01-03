import { 
  NEXT_SLIDE,
  PREV_SLIDE
} from '../constants/ActionTypes';
  
export function nextSlide() {
  return {
    type: NEXT_SLIDE
  };
}

export function prevSlide() {
  return {
    type: PREV_SLIDE
  };
}
