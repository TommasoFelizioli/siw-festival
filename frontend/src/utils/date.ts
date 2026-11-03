export function formattaData(data: string): string {
  const [anno, mese, giorno] = data.split('-')
  return `${giorno}/${mese}/${anno}`
}