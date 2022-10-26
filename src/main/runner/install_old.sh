#!/usr/bin/env sh
MICROVAULT_INSTALL_MODE=native
MICROVAULT_INSTALL_LOCATION=$HOME/.local/microvault
MICROVAULT_INSTALL_SOURCE=https://github.com/kpalatzky/microvault/releases/latest/download

cat <<'EOF' | cat
    __  ____               _    __            ____
   /  |/  (_)_____________| |  / /___ ___  __/ / /_
  / /|_/ / / ___/ ___/ __ \ | / / __ `/ / / / / __/
 / /  / / / /__/ /  / /_/ / |/ / /_/ / /_/ / / /_
/_/  /_/_/\___/_/   \____/|___/\__,_/\__,_/_/\__/

EOF

mkdir -p "$MICROVAULT_INSTALL_LOCATION"
wget -P "$MICROVAULT_INSTALL_LOCATION $MICROVAULT_INSTALL_SOURCE/microvault"
chomod +x "$MICROVAULT_INSTALL_LOCATION/microvault"
ln -s  "$MICROVAULT_INSTALL_LOCATION/microvault" "$MICROVAULT_INSTALL_LOCATION/miva"

PATH_VARIABLE='$PATH'
if [ -f "$HOME/.bashrc" ]; then
cat <<EOT >> "$HOME/.bashrc"

# Add MicroVault to path
export PATH=$MICROVAULT_INSTALL_LOCATION:$PATH_VARIABLE
EOT
fi

if [ -f "$HOME/.zshrc" ]; then
cat <<EOT >> "$HOME/.zshrc"

# Add MicroVault to path
export PATH=$MICROVAULT_INSTALL_LOCATION:$PATH_VARIABLE
EOT
fi

echo "=> Close and reopen your terminal to start using microvault or run the following to use it now:"
