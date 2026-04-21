/** Build a data URL for <img src> from raw Base64 stored in API (bytes, any common image type). */
export function avatarSrcFromBase64(b64: string | null | undefined): string | undefined {
  if (!b64) return undefined;
  try {
    const sample = atob(b64.slice(0, 48));
    const bytes = new Uint8Array(sample.length);
    for (let i = 0; i < sample.length; i++) bytes[i] = sample.charCodeAt(i);
    let mime = 'image/png';
    if (bytes[0] === 0xff && bytes[1] === 0xd8) mime = 'image/jpeg';
    else if (bytes[0] === 0x89 && bytes[1] === 0x50) mime = 'image/png';
    else if (bytes[0] === 0x47 && bytes[1] === 0x49) mime = 'image/gif';
    else if (bytes[0] === 0x52 && bytes[1] === 0x49) mime = 'image/webp';
    return `data:${mime};base64,${b64}`;
  } catch {
    return `data:image/png;base64,${b64}`;
  }
}
