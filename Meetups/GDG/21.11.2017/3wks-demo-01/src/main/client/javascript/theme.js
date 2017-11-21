import Color from 'color';
import lightBaseTheme from 'material-ui/styles/baseThemes/lightBaseTheme';

export default {
  ...lightBaseTheme,
  palette: {
    ...lightBaseTheme.palette,
    primary1Color: Color('#8bd1cc').darken(0.15).hex(),
    primary2Color: Color('#8bd1cc').darken(0.4).hex(),
    accent1Color: '#fdbe5a',
    accent2Color: Color('#fdbe5a').darken(0.2).hex(),
  },
  appBar: {
    color: '#2f353a',
  },
};
