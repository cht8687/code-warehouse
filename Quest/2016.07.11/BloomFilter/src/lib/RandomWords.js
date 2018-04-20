
/* Function to generate random given number of words with length 5 */
function makeRandomCharacters(num)
{
  let text = "";
  const possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
  for ( let i = 0; i < num; i++ )
    text += possible.charAt(Math.floor(Math.random() * possible.length));
  return text;
}

export function randomWords(number) {
  const rdomWords = [];
  for ( let i = 0; i < number; i++ ) {
    const genWords = makeRandomCharacters(5);
    rdomWords.push(genWords);
  }
  return rdomWords;
}
